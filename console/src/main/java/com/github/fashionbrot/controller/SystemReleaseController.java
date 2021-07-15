package com.github.fashionbrot.controller;

import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SystemReleaseEntity;
import com.github.fashionbrot.req.SystemReleaseReq;
import com.github.fashionbrot.service.SystemReleaseService;
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
 * 系统配置发布表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */

@MarsPermission(value="m:system:release")
@Controller
@RequestMapping("m/system/release")
@Api(tags="系统配置发布表")
@ApiSort(25271754)
public class SystemReleaseController  {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       m/system/release/page        权限：m:system:release:page
     * 数据列表    m/system/release/queryList   权限：m:system:release:queryList
     * 根据id查询  m/system/release/selectById  权限：m:system:release:selectById
     * 新增       m/system/release/insert      权限：m:system:release:insert
     * 修改       m/system/release/updateById  权限：m:system:release:updateById
     * 根据id删除  m/system/release/deleteById  权限：m:system:release:deleteById
     * 多个id删除  m/system/release/deleteByIds 权限：m:system:release:deleteByIds
     */


    @Autowired
    public SystemReleaseService service;



    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(SystemReleaseReq req) {
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
    public RespVo add(@RequestBody SystemReleaseEntity entity){
        service.save(entity);
        return RespVo.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody SystemReleaseEntity entity){
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