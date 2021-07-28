package com.github.fashionbrot.listener;

import com.github.fashionbrot.listener.annotation.MarsConfigListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarsListenerSourceTarget {

    private Object bean;
    private Method method;
    private MarsConfigListener listener;
}
