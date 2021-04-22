package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.DynamicDataLogEntity;
import com.github.fashionbrot.req.DynamicDataLogReq;
import com.github.fashionbrot.service.DynamicDataLogService;
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
 * 配置数据记录表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */

@MarsPermission(value="m:dynamic:data:log")
@Controller
@RequestMapping("m/dynamic/data/log")
@Api(tags="配置数据记录表")
@ApiSort(23816672)
public class DynamicDataLogController  {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       m/dynamic/data/log/page        权限：m:dynamic:data:log:page
     * 数据列表    m/dynamic/data/log/queryList   权限：m:dynamic:data:log:queryList
     * 根据id查询  m/dynamic/data/log/selectById  权限：m:dynamic:data:log:selectById
     * 新增       m/dynamic/data/log/insert      权限：m:dynamic:data:log:insert
     * 修改       m/dynamic/data/log/updateById  权限：m:dynamic:data:log:updateById
     * 根据id删除  m/dynamic/data/log/deleteById  权限：m:dynamic:data:log:deleteById
     * 多个id删除  m/dynamic/data/log/deleteByIds 权限：m:dynamic:data:log:deleteByIds
     */


    @Autowired
    public DynamicDataLogService service;



    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(DynamicDataLogReq req) {
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
    public RespVo add(@RequestBody DynamicDataLogEntity entity){
        service.save(entity);
        return RespVo.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody DynamicDataLogEntity entity){
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