package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.DynamicDataValueEntity;
import com.github.fashionbrot.req.DynamicDataValueReq;
import com.github.fashionbrot.service.DynamicDataValueService;
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
 * 动态配置数据表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */

@MarsPermission(value="m:dynamic:data:value")
@Controller
@RequestMapping("m/dynamic/data/value")
@Api(tags="动态配置数据表")
@ApiSort(23816661)
public class DynamicDataValueController  {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       m/dynamic/data/value/page        权限：m:dynamic:data:value:page
     * 数据列表    m/dynamic/data/value/queryList   权限：m:dynamic:data:value:queryList
     * 根据id查询  m/dynamic/data/value/selectById  权限：m:dynamic:data:value:selectById
     * 新增       m/dynamic/data/value/insert      权限：m:dynamic:data:value:insert
     * 修改       m/dynamic/data/value/updateById  权限：m:dynamic:data:value:updateById
     * 根据id删除  m/dynamic/data/value/deleteById  权限：m:dynamic:data:value:deleteById
     * 多个id删除  m/dynamic/data/value/deleteByIds 权限：m:dynamic:data:value:deleteByIds
     */


    @Autowired
    public DynamicDataValueService service;



    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(DynamicDataValueReq req) {
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
    public RespVo add(@RequestBody DynamicDataValueEntity entity){
        service.save(entity);
        return RespVo.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody DynamicDataValueEntity entity){
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