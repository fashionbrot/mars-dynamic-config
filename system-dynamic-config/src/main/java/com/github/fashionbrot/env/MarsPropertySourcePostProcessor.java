package com.github.fashionbrot.env;

import com.github.fashionbrot.ribbon.Server;
import com.github.fashionbrot.api.ApiConstant;
import com.github.fashionbrot.api.ForDataVoList;
import com.github.fashionbrot.config.GlobalMarsProperties;
import com.github.fashionbrot.ribbon.loadbalancer.BaseLoadBalancer;
import com.github.fashionbrot.ribbon.loadbalancer.ILoadBalancer;
import com.github.fashionbrot.util.CollectionUtil;
import com.github.fashionbrot.server.ServerHttpAgent;
import com.github.fashionbrot.util.BeanSystemUtil;
import com.github.fashionbrot.util.ObjectUtils;
import com.github.fashionbrot.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;


/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 */
@Slf4j
public class MarsPropertySourcePostProcessor implements BeanDefinitionRegistryPostProcessor, BeanFactoryPostProcessor,
        EnvironmentAware, Ordered, DisposableBean {

    public static final String BEAN_NAME = "marsPropertySourcePostProcessor";

    private ConfigurableEnvironment environment;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {


        GlobalMarsProperties globalMarsProperties = (GlobalMarsProperties) BeanSystemUtil.getSingleton(beanFactory, GlobalMarsProperties.BEAN_NAME);
        if (globalMarsProperties == null) {
            log.warn("globalMarsProperties is null");
            return;
        }
        String appCode = globalMarsProperties.getAppCode();
        String envCode = globalMarsProperties.getEnvCode();
        if (ObjectUtils.isEmpty(appCode) || ObjectUtils.isEmpty(envCode)) {
            if (log.isInfoEnabled()) {
                log.info(" mars init appCode is null or envCode is null");
            }
            return;
        }
        String serverAddress = globalMarsProperties.getServerAddress();
        if (StringUtil.isEmpty(serverAddress)) {
            log.warn(" ${mars.config.http.server-address} is null");
            return;
        }
        ILoadBalancer loadBalancer = new BaseLoadBalancer();
        loadBalancer.setServer(serverAddress);

        Server server = loadBalancer.chooseServer();
        if (server == null && globalMarsProperties.isEnableLocalCache()) {
            ServerHttpAgent.loadLocalConfig(globalMarsProperties,environment);
            return;
        }


        //????????????server version ?????????version ???????????????????????????????????????
        ForDataVoList dataVo = ServerHttpAgent.getForData(server,envCode,appCode,true);
        //?????? environment????????????????????? ??????, ????????????????????????version
        ServerHttpAgent.saveRemoteResponse(environment,globalMarsProperties,dataVo);
        //?????????????????????????????????????????????
        if ((dataVo==null || CollectionUtil.isEmpty(dataVo.getList()) ) &&   globalMarsProperties.isEnableLocalCache()){
            ServerHttpAgent.loadLocalConfig(globalMarsProperties,environment);
        }

    }


    /**
     * The order is closed to {@link ConfigurationClassPostProcessor#getOrder() HIGHEST_PRECEDENCE} almost.
     *
     * @return <code>Ordered.HIGHEST_PRECEDENCE + 1</code>
     * @see ConfigurationClassPostProcessor#getOrder()
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }


    @Override
    public void destroy() throws Exception {

    }
}