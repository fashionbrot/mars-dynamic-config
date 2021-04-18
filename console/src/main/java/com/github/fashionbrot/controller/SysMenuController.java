package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SysMenuEntity;
import com.github.fashionbrot.req.SysMenuReq;
import com.github.fashionbrot.service.SysMenuService;
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
 * 菜单表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */

@MarsPermission(value="sys:menu")
@Controller
@RequestMapping("sys/menu")
@Api(tags="菜单表")
@ApiSort(23744266)
public class SysMenuController  {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       sys/menu/page        权限：sys:menu:page
     * 数据列表    sys/menu/queryList   权限：sys:menu:queryList
     * 根据id查询  sys/menu/selectById  权限：sys:menu:selectById
     * 新增       sys/menu/insert      权限：sys:menu:insert
     * 修改       sys/menu/updateById  权限：sys:menu:updateById
     * 根据id删除  sys/menu/deleteById  权限：sys:menu:deleteById
     * 多个id删除  sys/menu/deleteByIds 权限：sys:menu:deleteByIds
     */


    @Autowired
    public SysMenuService service;



    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(SysMenuReq req) {
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

    @MarsPermission(":insert")
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public RespVo add(@RequestBody SysMenuEntity entity){
        service.save(entity);
        return RespVo.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody SysMenuEntity entity){
        service.updateById(entity);
        return RespVo.success();
    }


    @MarsPermission(":deleteById")
    @ApiOperation("根据id删除")
    @PostMapping("/deleteById")
    @ResponseBody
    public RespVo deleteById(Long id){
        service.removeById(id);
        return RespVo.success();
    }


    @MarsPermission(":deleteByIds")
    @ApiOperation("批量删除")
    @PostMapping("/deleteByIds")
    @ResponseBody
    public RespVo delete(@RequestBody Long[] ids){
        service.removeByIds(Arrays.asList(ids));
        return RespVo.success();
    }



}