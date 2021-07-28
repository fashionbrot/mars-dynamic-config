package com.github.fashionbrot.listener.annotation;



import java.lang.annotation.*;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 *
 * Annotation that marks a method as a listener for mars Config change.
 *
 * 方法参数类型支持两种 [java.lang.String][java.util.Properties]
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface MarsConfigListener {

    /**
     * listener filename
     * @return
     */
    String fileName();

    /**
     * It indicates that the currently bound property is auto-refreshed when mars configuration is changed.
     * @return
     */
    boolean autoRefreshed() default false;
}
