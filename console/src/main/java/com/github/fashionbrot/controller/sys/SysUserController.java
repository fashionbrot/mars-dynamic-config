package com.github.fashionbrot.controller.sys;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SysUserEntity;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.req.SysUserReq;
import com.github.fashionbrot.service.SysUserService;
import com.github.fashionbrot.service.UserLoginService;
import com.github.fashionbrot.util.CookieUtil;
import com.github.fashionbrot.vo.RespVo;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;


/**
 * 系统用户表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-17
 */

@MarsPermission(value="sys:user")
@Controller
@RequestMapping("sys/user")
@Api(tags="系统用户")
@ApiSort(23734562)
public class SysUserController {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       sys/user/page        权限：sys:user:page
     * 数据列表    sys/user/queryList   权限：sys:user:queryList
     * 根据id查询  sys/user/selectById  权限：sys:user:selectById
     * 新增       sys/user/insert      权限：sys:user:insert
     * 修改       sys/user/updateById  权限：sys:user:updateById
     * 根据id删除  sys/user/deleteById  权限：sys:user:deleteById
     * 多个id删除  sys/user/deleteByIds 权限：sys:user:deleteByIds
     */


    @Autowired
    public SysUserService service;

    @Autowired
    private UserLoginService userLoginService;


    @MarsPermission(":index")
    @GetMapping("/index")
    public String index() {
        return "/system/user/userInfo";
    }

    @GetMapping("/index/add")
    public String indexAdd() {
        return "/system/user/addInfo" ;
    }

    @RequestMapping("/edit")
    public String edit(Long id, ModelMap modelMap) {
        modelMap.put("info",service.getById(id));
        return "/system/user/editInfo" ;
    }

    @GetMapping("/profile/resetPwd")
    public String test(ModelMap mmap) {
        LoginModel loginModel = userLoginService.getLogin();
        mmap.put("user", loginModel);
        return "/system/user/resetPwd" ;
    }

    @ApiOperation("系统登录")
    @MarsLog
    @RequestMapping("/doLogin")
    @ResponseBody
    public RespVo login(String account,String password) {
        return RespVo.success(service.login(account, password));
    }

    @ApiOperation("退出登录")
    @RequestMapping(value = {"/logout"})
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response);
        return "/login";
    }


    @ApiOperation("修改密码")
    @MarsLog
    @RequestMapping("/resetPwd")
    @ResponseBody
    @MarsPermission("system:user:updatePwd")
    public RespVo updatePwd(String oldPassword, String newPassword) {
        service.updatePwd(oldPassword, newPassword);
        return RespVo.success();
    }


    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(SysUserReq req) {
        return RespVo.success(service.pageReq(req));
    }


    @MarsPermission(":queryList")
    @ApiOperation("数据列表")
    @GetMapping("/queryList")
    @ResponseBody
    public RespVo queryList(@RequestParam Map<String, Object> params){
        return  RespVo.success(service.listByMap(params));
    }


    @MarsPermission(":selectById")
    @ApiOperation("根据id查询")
    @PostMapping("/selectById")
    @ResponseBody
    public RespVo selectById(Long id){
        return RespVo.success(service.getById(id));
    }

    @MarsLog
    @MarsPermission(":insert")
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public RespVo add(@RequestBody SysUserEntity entity){
        service.add(entity);
        return RespVo.success();
    }


    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody SysUserEntity entity){
        service.edit(entity);
        return RespVo.success();
    }


    @MarsLog
    @MarsPermission(":deleteById")
    @ApiOperation("根据id删除")
    @PostMapping("/deleteById")
    @ResponseBody
    public RespVo deleteById(Long id){
        service.removeById(id);
        return RespVo.success();
    }






}