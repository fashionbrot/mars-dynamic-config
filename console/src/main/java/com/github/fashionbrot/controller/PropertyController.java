package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.PropertyEntity;
import com.github.fashionbrot.req.CopyPropertyReq;
import com.github.fashionbrot.req.PropertyReq;
import com.github.fashionbrot.service.PropertyService;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.vo.RespVo;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 属性表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */

@MarsPermission(value="m:property")
@Controller
@RequestMapping("m/property")
@Api(tags="属性表")
@ApiSort(23751667)
public class PropertyController  {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       m/property/page        权限：m:property:page
     * 数据列表    m/property/queryList   权限：m:property:queryList
     * 根据id查询  m/property/selectById  权限：m:property:selectById
     * 新增       m/property/insert      权限：m:property:insert
     * 修改       m/property/updateById  权限：m:property:updateById
     * 根据id删除  m/property/deleteById  权限：m:property:deleteById
     * 多个id删除  m/property/deleteByIds 权限：m:property:deleteByIds
     */


    @Autowired
    public PropertyService service;

    @GetMapping("/index")
    public String index(){
        return "/m/property/index";
    }

    @GetMapping("/add")
    public String add(){
        return "/m/property/add";
    }

    @GetMapping("/edit")
    public String edit(){
        return "/m/property/edit";
    }

    @GetMapping("/copyProperty")
    public String copyProperty(){
        return "/m/property/copyProperty";
    }

    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(PropertyReq req) {
        return RespVo.success(service.pageReq(req));
    }


    @MarsPermission(":queryList")
    @ApiOperation("数据列表")
    @GetMapping("/queryList")
    @ResponseBody
    public RespVo queryList(PropertyReq req){

        return  RespVo.success(service.queryList(req));
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
    public RespVo add(@RequestBody PropertyEntity entity){
        service.save(entity);
        return RespVo.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody PropertyEntity entity){
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

    @MarsPermission(":copyProperty")
    @ApiOperation("复制属性")
    @PostMapping("/copyProperty")
    @ResponseBody
    @Validated
    public RespVo copyProperty(CopyPropertyReq req){
        service.copyProperty(req);
        return RespVo.success();
    }


}