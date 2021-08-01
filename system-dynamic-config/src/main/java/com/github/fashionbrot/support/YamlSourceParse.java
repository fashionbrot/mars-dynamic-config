package com.github.fashionbrot.support;


import com.github.fashionbrot.enums.ConfigTypeEnum;
import com.github.fashionbrot.util.CollectionUtil;
import com.github.fashionbrot.util.YamlParser;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Properties;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 */

@Slf4j
public class YamlSourceParse  implements SourceParse {


    @Override
    public Properties parse( String context){
        Properties result = new Properties();
        Map<String, Object> map = YamlParser.yamlToFlattenedMap(context);
        if (CollectionUtil.isNotEmpty(map)){
            result.putAll(map);
        }
        return result;
    }

    @Override
    public Properties fileToProperties(File file) {
        Properties properties=new Properties();
        Yaml yaml =new Yaml();
        try {
            Map<String,Object> map = yaml.load(new FileInputStream(file));
            if (CollectionUtil.isNotEmpty(map)) {
                properties.putAll(map);
            }
        } catch (FileNotFoundException e) {
            log.error("fileToProperties error",e);
        }
        return properties;
    }

    @Override
    public ConfigTypeEnum sourceType() {
        return ConfigTypeEnum.YAML;
    }




}
