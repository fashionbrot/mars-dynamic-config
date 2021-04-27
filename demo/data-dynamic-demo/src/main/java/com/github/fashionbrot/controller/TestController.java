package com.github.fashionbrot.controller;

import com.github.fashionbrot.DataDynamicCache;
import com.github.fashionbrot.model.BannerModel;
import com.github.fashionbrot.ribbon.util.CollectionUtil;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

@Controller
public class TestController {



    @RequestMapping("test")
    @ResponseBody
    public Object test(){
        return DataDynamicCache.getTemplateObject("banner");
    }

    @RequestMapping("test1")
    @ResponseBody
    public Object test1(){
        return DataDynamicCache.getTemplateObject("banner");
    }

    @RequestMapping("test2")
    @ResponseBody
    public Object test2(){
        List<BannerModel> banner = DataDynamicCache.getDeepTemplateObject("banner");
        if (CollectionUtil.isNotEmpty(banner)){
            for (BannerModel b :banner){
                b.setTitle("abc");
            }
        }
        return banner;
    }

}
