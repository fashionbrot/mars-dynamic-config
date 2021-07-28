package com.github.fashionbrot.controller;

import com.github.fashionbrot.DataDynamicCache;
import com.github.fashionbrot.model.BannerModel;
import com.github.fashionbrot.ribbon.util.CollectionUtil;
import com.github.fashionbrot.value.MarsValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SystemController {


    @MarsValue(value = "${abc}",autoRefreshed = true)
    private String abc;

    @RequestMapping("systemConfigTest")
    @ResponseBody
    public Object test(){
        return abc;
    }


}
