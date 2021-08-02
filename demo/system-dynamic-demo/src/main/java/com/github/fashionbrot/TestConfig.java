package com.github.fashionbrot;


import com.github.fashionbrot.properties.annotation.MarsConfigurationProperties;
import com.github.fashionbrot.properties.annotation.MarsIgnoreField;
import com.github.fashionbrot.properties.annotation.MarsProperty;
import lombok.Data;

@MarsConfigurationProperties(fileName = "test",autoRefreshed = true)
@Data
public class TestConfig {

    @MarsProperty("abc")
    public String name ;

//    @MarsProperty("test")
    @MarsIgnoreField
    public String appName ;

    @MarsIgnoreField
    private String sgrTest;

}
