package com.github.fashionbrot.config;

import com.github.fashionbrot.config.annotation.EnableSystemDynamicConfig;
import com.github.fashionbrot.util.BeanSystemUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import static org.springframework.core.annotation.AnnotationAttributes.fromMap;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 */
public class MarsConfigBeanDefinitionRegistrar  implements ImportBeanDefinitionRegistrar, EnvironmentAware, BeanFactoryAware {



    private Environment environment;

    private ConfigurableListableBeanFactory beanFactory;


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        Assert.isInstanceOf(ConfigurableListableBeanFactory.class, beanFactory, "MarsConfigBeanDefinitionRegistrar requires a ConfigurableListableBeanFactory");
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {


        AnnotationAttributes attributes = fromMap(metadata.getAnnotationAttributes(EnableSystemDynamicConfig.class.getName()));

        /**
         * Register http Global mars Properties Bean
         */
        BeanSystemUtil.registerGlobalMarsProperties(attributes, registry, environment,beanFactory);
        /**
         * Register applicationContextHolder Bean
         */
        BeanSystemUtil.registerApplicationContextHolder(registry);

        /**
         * register MarsConfigurationPropertiesBindingPostProcessor bean
         */
        BeanSystemUtil.registerMarsConfigurationPropertiesBindingPostProcessor(registry);
        /**
         * register MarsValue config bean
         */
        BeanSystemUtil.registerMarsValueAnnotationBeanPostProcessor(registry);

        /**
         * register listenerPostProcessor bean
         */
        BeanSystemUtil.registerMarsListener(registry);

        BeanSystemUtil.registerMarsPropertySourcePostProcessor(registry);

        BeanSystemUtil.registerMarsTimerHttpBeanPostProcessor(registry);
    }


}
