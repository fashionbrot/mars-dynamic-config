package com.github.fashionbrot.annotation;

import java.lang.annotation.*;

/**
 * 是否开启日志持久化
 */
@Documented
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MarsLog {

    /**
     * 是否持久化到 mysql
     * @return
     */
    boolean value() default true;

}
