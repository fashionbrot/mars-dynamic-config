package com.github.fashionbrot.controller;


import com.github.fashionbrot.entity.SysMenuEntity;
import com.github.fashionbrot.service.SysMenuService;
import com.github.fashionbrot.service.SysUserService;
import com.github.fashionbrot.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;


    @GetMapping("/index")
    public String index(ModelMap mmap){
        setModelMap(mmap);
        return "index";
    }

    @GetMapping("/index-topnav")
    public String indexTopnav(ModelMap mmap){
        setModelMap(mmap);
        return "index-topnav";
    }


    private void setModelMap(ModelMap mmap) {
        List<SysMenuEntity> menus =sysMenuService.loadAllMenu();
        mmap.put("menus", menus);
        mmap.put("user", sysUserService.getLogin());
    }

    @GetMapping("/build")
    public String bulid(){
        return "build/build";
    }


    // 切换主题
    @GetMapping("/system/switchSkin")
    public String switchSkin() {
        return "skin";
    }

    // 切换菜单
    @GetMapping("/system/menuStyle/{style}")
    public void menuStyle(@PathVariable String style, HttpServletResponse response) {
        CookieUtil.setCookie(response, "nav-style", style);
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        return "main";
    }



}
