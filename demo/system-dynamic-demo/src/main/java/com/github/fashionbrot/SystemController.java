package com.github.fashionbrot;

import com.github.fashionbrot.listener.annotation.MarsConfigListener;
import com.github.fashionbrot.value.MarsValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Properties;

@Controller
public class SystemController {


    @MarsValue(value = "${abc}",autoRefreshed = true)
    private String abc;
    @Autowired
    private Environment environment;

    @RequestMapping("test1")
    @ResponseBody
    public Object test(){
        return abc;
    }


    @RequestMapping("systemConfigTest2")
    @ResponseBody
    public Object test(String key){
        return environment.getProperty(key);
    }

    /**
     * 方式2 根据类获取 配置
     */
    @Autowired
    private TestConfig testConfig;


    @RequestMapping("/test2")
    @ResponseBody
    public String test2(){
        return testConfig.appName+":"+testConfig.name;
    }

    /**
     * 方式三根据 配置发生变化获取到监听
     * @param
     */
    @MarsConfigListener(fileName = "test",autoRefreshed = true)
    public void testP(Properties properties){
        System.out.println("11111:"+properties.toString());
    }

}
