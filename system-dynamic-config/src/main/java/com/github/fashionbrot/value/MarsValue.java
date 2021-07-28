package com.github.fashionbrot.value;

import java.lang.annotation.*;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 *
 * Annotation which extends value to support auto-refresh
 *
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MarsValue {

    /**
     * The actual value expression: e.g. "#{systemProperties.myProp}".
     * @return
     */
    String value();

    /**
     * It indicates that the currently bound property is auto-refreshed when mars configuration is changed.
     * @return
     */
    boolean autoRefreshed() default false;

}