package com.github.fashionbrot.config;

import com.github.fashionbrot.interceptor.BackstageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Autowired
    private Environment environment;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/_src/**").addResourceLocations("classpath:/static/_src/core/");
        String profile = environment.getProperty("spring.profiles.active","dev");
        if ("test".equals(profile) || "dev".equals(profile)){
            registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }


    @Bean
    public BackstageInterceptor getInterceptor(){
        return new BackstageInterceptor();
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(getInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/login")
                .excludePathPatterns("/sys/user/doLogin")
                .excludePathPatterns("/sys/user/logout")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/img/**")
                .excludePathPatterns("/ruoyi/**")
                .excludePathPatterns("/ajax/**")
                .excludePathPatterns("/file/**")
                .excludePathPatterns("/fonts/**")
                .excludePathPatterns("/i18n/**")
                .excludePathPatterns("/favicon.ico")
                .excludePathPatterns("/build")
                .excludePathPatterns("/401")
                .excludePathPatterns("/error")
                .excludePathPatterns("/open/**")
        ;
    }


}
