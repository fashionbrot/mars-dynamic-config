package com.github.fashionbrot;

import com.github.fashionbrot.annotation.EnableDataDynamicConfig;
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

import static com.github.fashionbrot.util.BeanUtil.*;
import static org.springframework.core.annotation.AnnotationAttributes.fromMap;


public class DataDynamicBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware, BeanFactoryAware {



    private Environment environment;

    private ConfigurableListableBeanFactory beanFactory;


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        Assert.isInstanceOf(ConfigurableListableBeanFactory.class, beanFactory, "DataDynamicBeanDefinitionRegistrar requires a ConfigurableListableBeanFactory");
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {


        AnnotationAttributes attributes = fromMap(metadata.getAnnotationAttributes(EnableDataDynamicConfig.class.getName()));

        /**
         * Register http Global mars Properties Bean
         */
        registerGlobalDataDynamicProperties(attributes, registry, environment,beanFactory);

        registerConfigPostProcessor(registry);

        registerHttpBeanPostProcessor(registry);
    }


}
