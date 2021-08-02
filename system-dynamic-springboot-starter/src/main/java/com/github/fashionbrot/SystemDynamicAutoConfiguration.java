package com.github.fashionbrot;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(SystemDynamicConfigurationProperties.class)
@Import(value = {
        SystemDynamicConfigurationProperties.class
})
public class SystemDynamicAutoConfiguration {



}
