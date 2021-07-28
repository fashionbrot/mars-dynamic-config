package com.github.fashionbrot.support;



import com.github.fashionbrot.enums.ConfigTypeEnum;

import java.io.File;
import java.util.Properties;

public interface SourceParse {

    Properties parse(String context);

    Properties fileToProperties(File file);

    ConfigTypeEnum sourceType();

}
