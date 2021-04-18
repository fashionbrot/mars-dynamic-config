package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SysRoleEntity;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.req.SysRoleReq;
import com.github.fashionbrot.service.SysRoleService;
import com.github.fashionbrot.service.SysUserService;
import com.github.fashionbrot.util.CaffeineCacheUtil;
import com.github.fashionbrot.vo.RespVo;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 角色表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */

@MarsPermission(value="sys:role")
@Controller
@RequestMapping("sys/role")
@Api(tags="角色表")
@ApiSort(23744266)
public class SysRoleController  {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       sys/role/page        权限：sys:role:page
     * 数据列表    sys/role/queryList   权限：sys:role:queryList
     * 根据id查询  sys/role/selectById  权限：sys:role:selectById
     * 新增       sys/role/insert      权限：sys:role:insert
     * 修改       sys/role/updateById  权限：sys:role:updateById
     * 根据id删除  sys/role/deleteById  权限：sys:role:deleteById
     * 多个id删除  sys/role/deleteByIds 权限：sys:role:deleteByIds
     */


    @Autowired
    public SysRoleService service;
    @Autowired
    private SysUserService sysUserService;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "system/role/role";
    }

    @GetMapping("/index/add")
    public String indexAdd(){
        return "system/role/add";
    }

    @GetMapping("/index/edit")
    public String indexEdit(){
        return "system/role/edit";
    }



    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(SysRoleReq req) {
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
    public RespVo add(@RequestBody SysRoleEntity entity){
        service.add(entity);
        return RespVo.success();
    }


    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody SysRoleEntity entity){
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