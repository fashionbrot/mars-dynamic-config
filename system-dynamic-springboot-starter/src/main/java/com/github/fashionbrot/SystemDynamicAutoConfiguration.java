package com.github.fashionbrot;


import com.github.fashionbrot.config.MarsConfigBeanDefinitionRegistrar;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(SystemDynamicConfigurationProperties.class)
@Import(value = {
        MarsConfigBeanDefinitionRegistrar.class
})
public class SystemDynamicAutoConfiguration {




}
