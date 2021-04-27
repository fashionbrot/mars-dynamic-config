package com.github.fashionbrot.util;

import com.github.fashionbrot.DataDynamicConsts;
import com.github.fashionbrot.GlobalDataDynamicProperties;
import com.github.fashionbrot.event.DataDynamicPostProcessor;
import com.github.fashionbrot.event.HttpBeanPostProcessor;
import com.github.fashionbrot.ribbon.util.CollectionUtil;
import com.github.fashionbrot.ribbon.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Properties;

import static org.springframework.util.SystemPropertyUtils.PLACEHOLDER_PREFIX;
import static org.springframework.util.SystemPropertyUtils.PLACEHOLDER_SUFFIX;


@Slf4j
public class BeanUtil {


    public static void registerGlobalDataDynamicProperties(AnnotationAttributes attributes, BeanDefinitionRegistry registry, PropertyResolver environment, ConfigurableListableBeanFactory beanFactory) {
        GlobalDataDynamicProperties globalMarsProperties = null;
        if (CollectionUtil.isNotEmpty(attributes)){
            Properties globalProperties = resolveProperties(attributes, environment);
            globalMarsProperties = GlobalDataDynamicProperties.builder()
                    .appCode(getProperties(globalProperties, "appCode"))
                    .envCode(getProperties(globalProperties, "envCode"))
                    .serverAddress(getProperties(globalProperties, "serverAddress"))
                    .listenLongPollMs(StringUtil.parseLong(getProperties(globalProperties, "listenLongPollMs"), 10000L))
                    .localCachePath(getProperties(globalProperties,"localCachePath"))
                    .build();
        }else{
            globalMarsProperties = GlobalDataDynamicProperties.builder()
                    .appCode(getEnvValue(environment, DataDynamicConsts.APP_CODE,""))
                    .envCode(getEnvValue(environment, DataDynamicConsts.ENV_CODE,""))
                    .serverAddress(getEnvValue(environment, DataDynamicConsts.SERVER_ADDRESS,""))
                    .listenLongPollMs(StringUtil.parseLong(getEnvValue(environment, DataDynamicConsts.LISTEN_LONG_POLL_MS, "10000"),10000L))
                    .localCachePath(getEnvValue(environment, DataDynamicConsts.LOCAL_CACHE_PATH,""))
                    .build();
        }
        registerSingleton(registry, GlobalDataDynamicProperties.BEAN_NAME, globalMarsProperties);
    }

    private static String getEnvValue(PropertyResolver environment,String key,String defaultValue){
        if (environment!=null && environment.containsProperty(key)){
            String value = environment.getProperty(key);
            if (StringUtil.isEmpty(value)){
                value = defaultValue;
            }
            return value;
        }
        return "";
    }


    public static void registerConfigPostProcessor(BeanDefinitionRegistry registry) {
        registerInfrastructureBeanIfAbsent(registry, DataDynamicPostProcessor.BEAN_NAME, DataDynamicPostProcessor.class);
    }


    public static void registerHttpBeanPostProcessor(BeanDefinitionRegistry registry) {
        registerInfrastructureBeanIfAbsent(registry, HttpBeanPostProcessor.BEAN_NAME, HttpBeanPostProcessor.class);
    }

    /**
     * Resolve placeholders of properties via specified {@link PropertyResolver} if present
     *
     * @param properties       The properties
     * @param propertyResolver {@link PropertyResolver} instance, for instance, {@link Environment}
     * @return a new instance of {@link Properties} after resolving.
     */
    public static Properties resolveProperties(Map<?, ?> properties, PropertyResolver propertyResolver) {
        PropertiesPlaceholderResolver propertiesPlaceholderResolver = new PropertiesPlaceholderResolver(propertyResolver);
        return propertiesPlaceholderResolver.resolve(properties);
    }

    /**
     * Register an object to be Singleton Bean
     *
     * @param registry        {@link BeanDefinitionRegistry}
     * @param beanName        bean name
     * @param singletonObject singleton object
     */
    public static void registerSingleton(BeanDefinitionRegistry registry, String beanName, Object singletonObject) {
        SingletonBeanRegistry beanRegistry = null;
        if (registry instanceof SingletonBeanRegistry) {
            beanRegistry = (SingletonBeanRegistry) registry;
        } else if (registry instanceof AbstractApplicationContext) {
            // Maybe AbstractApplicationContext or its sub-classes
            beanRegistry = ((AbstractApplicationContext) registry).getBeanFactory();
        }
        // Register Singleton Object if possible
        if (beanRegistry != null) {
            beanRegistry.registerSingleton(beanName, singletonObject);
        }
    }

    public static Object getSingleton(BeanFactory registry, String beanName) {
        SingletonBeanRegistry beanRegistry = null;
        if (registry instanceof SingletonBeanRegistry) {
            beanRegistry = (SingletonBeanRegistry) registry;
        } else if (registry instanceof AbstractApplicationContext) {
            // Maybe AbstractApplicationContext or its sub-classes
            beanRegistry = ((AbstractApplicationContext) registry).getBeanFactory();
        }
        if (beanRegistry != null) {
            return beanRegistry.getSingleton(beanName);
        }
        return null;
    }

    /**
     * Register Infrastructure Bean if absent
     *
     * @param registry        {@link BeanDefinitionRegistry}
     * @param beanName        the name of bean
     * @param beanClass       the class of bean
     * @param constructorArgs the arguments of {@link Constructor}
     */
    public static void registerInfrastructureBeanIfAbsent(BeanDefinitionRegistry registry, String beanName, Class<?> beanClass,
                                                          Object... constructorArgs) {
        if (!registry.containsBeanDefinition(beanName)) {
            registerInfrastructureBean(registry, beanName, beanClass, constructorArgs);
        }
    }

    /**
     * Register Infrastructure Bean
     *
     * @param registry        {@link BeanDefinitionRegistry}
     * @param beanName        the name of bean
     * @param beanClass       the class of bean
     * @param constructorArgs the arguments of {@link Constructor}
     */
    public static void registerInfrastructureBean(BeanDefinitionRegistry registry, String beanName, Class<?> beanClass,
                                                  Object... constructorArgs) {
        // Build a BeanDefinition for serviceFactory class
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(beanClass);
        for (Object constructorArg : constructorArgs) {
            beanDefinitionBuilder.addConstructorArgValue(constructorArg);
        }
        // ROLE_INFRASTRUCTURE
        beanDefinitionBuilder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        // Register
        registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
    }



    public static String getProperties(Properties properties, String key) {

        String value = properties.containsKey(key) ? properties.getProperty(key) : "";
        if (ObjectUtil.isNotEmpty(value) && value.startsWith(PLACEHOLDER_PREFIX)) {
            return null;
        }
        if (ObjectUtil.isNotEmpty(value) && value.endsWith(PLACEHOLDER_SUFFIX)) {
            return null;
        }
        return value;
    }



}
