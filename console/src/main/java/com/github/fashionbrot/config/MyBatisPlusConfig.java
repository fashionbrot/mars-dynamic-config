package com.github.fashionbrot.config;

import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Configurable
@Component
public class MyBatisPlusConfig {
    /**
     * 逻辑删除插件
     * @return
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector();
    }

    @Bean
    public MybatisPlusInterceptor optimisticLockerInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}
