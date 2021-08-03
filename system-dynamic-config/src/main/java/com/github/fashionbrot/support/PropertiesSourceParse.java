package com.github.fashionbrot.support;

import com.github.fashionbrot.enums.ConfigTypeEnum;
import com.github.fashionbrot.exception.CreatePropertySourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 */
@Slf4j
public class PropertiesSourceParse implements SourceParse {


    @Override
    public Properties parse(String context) {
        Properties properties = new Properties();
        try {
            if (StringUtils.hasText(context)) {
                properties.load(new StringReader(context));
            }
        } catch (IOException e) {
            throw new CreatePropertySourceException(e);
        }
        return properties;
    }

    @Override
    public Properties fileToProperties(File file) {
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
        } catch (IOException e) {
            log.error("fileToProperties error",e);
        }finally {
            if (fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    @Override
    public ConfigTypeEnum sourceType() {
        return ConfigTypeEnum.PROPERTIES;
    }


}
