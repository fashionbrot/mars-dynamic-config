package com.github.fashionbrot;


import org.springframework.core.Ordered;

import java.util.Map;

public abstract class MarsTemplateKeyMapping implements Ordered {

    /**
     * 必须实现java 序列化
     * @return Map<String,Class>
     */
    public abstract Map<String,Class> initTemplateKeyClass();

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2;
    }
}

