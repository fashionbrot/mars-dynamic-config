package com.github.fashionbrot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 */
@Getter
@AllArgsConstructor
public enum ConfigTypeEnum {

    /**
     * config type is "properties"
     */
    PROPERTIES("PROPERTIES"),

    /**
     * config type is "text"
     */
    TEXT("TEXT"),

    /**
     * config type is "yaml"
     */
    YAML("YAML"),

    CONF("CONF"),

    NONE("");

    String type;


    private static Map<String, ConfigTypeEnum> map = new HashMap<String, ConfigTypeEnum>();

    static {
        Arrays.stream(ConfigTypeEnum.values()).forEach(configTypeMenu -> {
            map.put(configTypeMenu.getType(),configTypeMenu);
        });
    }

    public static ConfigTypeEnum valueTypeOf(String type){
        return map.get(type);
    }
}
