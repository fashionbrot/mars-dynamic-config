package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.DynamicDataEntity;
import com.github.fashionbrot.req.DynamicDataReq;
import com.github.fashionbrot.service.DynamicDataService;
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
 * 动态配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */

@MarsPermission(value="m:dynamic:data")
@Controller
@RequestMapping("m/dynamic/data")
@Api(tags="动态配置表")
@ApiSort(23816652)
public class DynamicDataController  {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       m/dynamic/data/page        权限：m:dynamic:data:page
     * 数据列表    m/dynamic/data/queryList   权限：m:dynamic:data:queryList
     * 根据id查询  m/dynamic/data/selectById  权限：m:dynamic:data:selectById
     * 新增       m/dynamic/data/insert      权限：m:dynamic:data:insert
     * 修改       m/dynamic/data/updateById  权限：m:dynamic:data:updateById
     * 根据id删除  m/dynamic/data/deleteById  权限：m:dynamic:data:deleteById
     * 多个id删除  m/dynamic/data/deleteByIds 权限：m:dynamic:data:deleteByIds
     */


    @Autowired
    public DynamicDataService service;

    @MarsPermission(":/index")
    @GetMapping("/index")
    public String index(){
        return "/m/dynamicData/index";
    }

    @MarsPermission(":/add")
    @GetMapping("/add")
    public String add(){
        return "/m/dynamicData/add";
    }

    @MarsPermission(":/edit")
    @GetMapping("/edit")
    public String edit(){
        return "/m/dynamicData/edit";
    }



    @MarsPermission(":index")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(DynamicDataReq req) {
        return RespVo.success(service.pageReq(req));
    }



    @ApiOperation("数据列表")
    @GetMapping("/queryList")
    @ResponseBody
    public RespVo queryList(@RequestParam Map<String, Object> params){
        return  RespVo.success(service.listByMap(params));
    }



    @ApiOperation("根据id查询")
    @PostMapping("/selectById")
    @ResponseBody
    public RespVo selectById(Long id){
        return RespVo.success(service.selectById(id));
    }
    

    @MarsPermission(":insert")
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public RespVo add(@RequestBody DynamicDataEntity entity){
        service.add(entity);
        return RespVo.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody DynamicDataEntity entity){
        service.edit(entity);
        return RespVo.success();
    }


    @MarsPermission(":deleteById")
    @ApiOperation("根据id删除")
    @PostMapping("/deleteById")
    @ResponseBody
    public RespVo deleteById(Long id){
        service.deleteById(id);
        return RespVo.success();
    }





}