package com.github.fashionbrot.properties.annotation;


import java.lang.annotation.*;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 *
 * An annotation for mars Property name of  mars Configuration to bind a field from
 * annotated {@link  com.github.fashionbrot.properties.annotation.MarsConfigurationProperties} Properties Object.
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MarsProperty {


    /**
     * The property name of mars Configuration to bind a field
     *
     * @return property name
     */
    String value();
}
