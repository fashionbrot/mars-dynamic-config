package com.github.fashionbrot.event;

import com.github.fashionbrot.*;
import com.github.fashionbrot.ribbon.Server;
import com.github.fashionbrot.ribbon.loadbalancer.BaseLoadBalancer;
import com.github.fashionbrot.ribbon.loadbalancer.ILoadBalancer;
import com.github.fashionbrot.util.CollectionUtil;
import com.github.fashionbrot.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

import java.util.Map;

/**
 * spring 容器初始化过程，第二个执行当前类的bean 前置操作
 */
@Slf4j
public  class DataDynamicPostProcessor implements BeanFactoryAware,BeanFactoryPostProcessor, Ordered {

    public static final String BEAN_NAME="mars-DataDynamicPostProcessor";

    private BeanFactory beanFactory;



    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanNamesForType = beanFactory.getBeanNamesForType(MarsTemplateKeyMapping.class);
        if (beanNamesForType!=null && beanNamesForType.length>0) {
            MarsTemplateKeyMapping marsTemplateKeyMapping = beanFactory.getBean(MarsTemplateKeyMapping.class);
            if (marsTemplateKeyMapping != null) {
                Map<String, Class> stringClassMap = marsTemplateKeyMapping.initTemplateKeyClass();
                if (CollectionUtil.isNotEmpty(stringClassMap)) {
                    DataDynamicCache.setFormatClass(stringClassMap);
                }
            }
        }

        if (log.isInfoEnabled()) {
            log.info(BEAN_NAME+" postProcessBeanFactory ");
        }
        GlobalDataDynamicProperties properties = (GlobalDataDynamicProperties) BeanUtil.getSingleton(beanFactory,GlobalDataDynamicProperties.BEAN_NAME);
        if (properties==null){
            log.error(BEAN_NAME+" config properties is null");
            return;
        }


        ILoadBalancer loadBalancer = new BaseLoadBalancer();
        loadBalancer.setServer(properties.getServerAddress());

        Server server = loadBalancer.chooseServer();
        if (server==null){
            log.error("loadBalancer server is null  rule:{} ping:{}", loadBalancer.getRule().getClass().getName(), loadBalancer.getPing().getClass().getName());
            return;
        }

        if (!HttpService.checkVersion(server,properties)){
            HttpService.getData(server,properties,true);
        }
    }



    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE+3;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }



}
