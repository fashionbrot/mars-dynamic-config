package com.github.fashionbrot.config.annotation;

import com.github.fashionbrot.config.MarsConfigBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MarsConfigBeanDefinitionRegistrar.class)
public @interface EnableSystemDynamicConfig {

    String APP_CODE = "${mars.dynamic.system.app-code}";

    String ENV_CODE = "${mars.dynamic.system.env-code}";

    String SERVER_ADDRESS = "${mars.dynamic.system.server-address}";
    /**
     * Whether to enable local caching
     */
    String ENABLE_LOCAL_CACHE = "${mars.dynamic.system.enable-local-cache}";
    /**
     * Local cache file path
     */
    String LOCAL_CACHE_PATH = "${mars.dynamic.system.local-cache-path}";


    String LISTEN_LONG_POLL_MS = "${mars.dynamic.system.listen-long-poll-ms}";



    /**
     * project name
     * @return
     */
    String appCode() default APP_CODE;

    /**
     * profiles env code
     * @return
     */
    String envCode() default ENV_CODE;

    /**
     * mars server address
     * @return
     */
    String serverAddress() default SERVER_ADDRESS;

    /**
     * listen long poll timeout  default 30000 ms
     */
    String listenLongPollMs() default LISTEN_LONG_POLL_MS;

    /**
     * Whether to enable local caching
     * @return
     */
    String enableLocalCache() default ENABLE_LOCAL_CACHE;

    /**
     * Local cache file path
     * @return
     */
    String localCachePath() default LOCAL_CACHE_PATH;
}
