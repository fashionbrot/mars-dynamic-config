package com.github.fashionbrot.env;

import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 */
public class MarsPropertySource extends PropertiesPropertySource {


    public MarsPropertySource( String name, Properties source ) {
        super(name, source);
    }
}
