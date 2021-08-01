package com.github.fashionbrot.listener;

import com.github.fashionbrot.api.ApiConstant;
import com.github.fashionbrot.env.MarsPropertySource;
import com.github.fashionbrot.event.MarsListenerEvent;
import com.github.fashionbrot.listener.annotation.MarsConfigListener;
import com.github.fashionbrot.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 *
 */
@Slf4j
public class MarsConfigListenerMethodProcessor extends AbstractAnnotationListenerMethodProcessor<MarsConfigListener>
        implements ApplicationListener<MarsListenerEvent>, EnvironmentAware {

    private static Map<String,MarsListenerSourceTarget> targetMap = new ConcurrentHashMap<>();

    public static final String BEAN_NAME = "MarsConfigListenerMethodProcessor";

    private ConfigurableEnvironment environment;

    @Override
    protected void processListenerMethod(String beanName, final Object bean, Class<?> beanClass,
                                         final MarsConfigListener listener, final Method method) {


        String fileName =  listener.fileName();
        Assert.isTrue(StringUtils.hasText(fileName), "fileName must have content");

        MarsListenerSourceTarget target = MarsListenerSourceTarget.builder()
                .bean(bean)
                .method(method)
                .listener(listener)
                .build();
        targetMap.put(fileName,target);

        invokeMethod(target);

    }

    private void invokeMethod(MarsListenerSourceTarget target){
        String fileName = target.getListener().fileName();
        Class<?>[] parameterTypes = target.getMethod().getParameterTypes();
        if (parameterTypes.length!=1){
            log.error(" processListenerMethod invokeMethod target method parameterType can not be empty ");
            return;
        }
        if (parameterTypes[0]!=Properties.class){
            log.error(" MarsConfigListener invokeMethod target method parameterType not Properties.class  targetClass:{}",target);
            return;
        }
        MarsPropertySource marsPropertySource = (MarsPropertySource) environment.getPropertySources().get(ApiConstant.NAME+fileName);
        if (marsPropertySource!=null) {
            Map<String, Object> source = marsPropertySource.getSource();
            if (CollectionUtil.isNotEmpty(source)){
                Properties p = new Properties();
                p.putAll(source);
                ReflectionUtils.invokeMethod(target.getMethod(), target.getBean(), p);
            }
        }
    }

    @Override
    public void onApplicationEvent(MarsListenerEvent marsListenerEvent) {
        MarsListenerSourceTarget target =targetMap.get(marsListenerEvent.getFileName());
        if (target!=null && target.getListener().autoRefreshed()){
            invokeMethod(target);
        }
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }
}
