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
     * project Code
     * @return
     */
    String appCode() default DataDynamicConsts.APP_CODE;

    /**
     * profiles env code
     * @return
     */
    String envCode() default DataDynamicConsts.ENV_CODE;

    /**
     * mars server address
     * @return
     */
    String serverAddress() default DataDynamicConsts.SERVER_ADDRESS;

    /**
     * listen long poll timeout  default 10000 ms
     */
    String listenLongPollMs() default DataDynamicConsts.LISTEN_LONG_POLL_MS;


    /**
     * 集群配置
     * @return
     */
    String cluster() default DataDynamicConsts.CLUSTER;

    /**
     * 预留方法
     * Local cache file path
     * @return
     */
    String localCachePath() default DataDynamicConsts.LOCAL_CACHE_PATH;
}
