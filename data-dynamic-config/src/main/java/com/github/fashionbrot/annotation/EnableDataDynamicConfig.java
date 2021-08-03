package com.github.fashionbrot.annotation;

import com.github.fashionbrot.DataDynamicBeanDefinitionRegistrar;
import com.github.fashionbrot.DataDynamicConsts;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;



@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DataDynamicBeanDefinitionRegistrar.class)
public @interface EnableDataDynamicConfig {


    /**
     * project code
     */
    String appCode() default DataDynamicConsts.APP_CODE;

    /**
     * env code
     */
    String envCode() default DataDynamicConsts.ENV_CODE;

    /**
     * server address
     */
    String serverAddress() default DataDynamicConsts.SERVER_ADDRESS;


    /**
     * listen long poll timeout default 10000 ms
     */
    String listenLongPollMs() default DataDynamicConsts.LISTEN_LONG_POLL_MS;


    String localCachePath() default DataDynamicConsts.LOCAL_CACHE_PATH;
}
