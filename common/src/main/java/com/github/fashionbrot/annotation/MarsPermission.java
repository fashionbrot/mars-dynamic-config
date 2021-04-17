package com.github.fashionbrot.annotation;

import java.lang.annotation.*;

/**
 * 权限注解
 */
@Documented
@Target({ ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MarsPermission {

    /**
     * 权限code
     * @return
     */
    String value();

}
