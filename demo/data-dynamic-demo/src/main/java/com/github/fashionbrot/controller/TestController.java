package com.github.fashionbrot.controller;

import com.github.fashionbrot.DataDynamicCache;
import com.github.fashionbrot.model.BannerModel;
import com.github.fashionbrot.util.CollectionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {


    /**
     * 浅拷贝
     * @return
     */
    @RequestMapping("test1")
    @ResponseBody
    public Object test1(){
        return DataDynamicCache.getTemplateObject("banner");
    }

    /**
     * 深拷贝
     * @return
     */
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
