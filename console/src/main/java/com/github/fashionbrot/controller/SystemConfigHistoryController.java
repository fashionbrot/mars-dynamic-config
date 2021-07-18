package com.github.fashionbrot.controller;

import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SystemConfigHistoryEntity;
import com.github.fashionbrot.req.SystemConfigHistoryReq;
import com.github.fashionbrot.service.SystemConfigHistoryService;
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
 * 应用系统配置历史表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */

@MarsPermission(value="m:system:config:history")
@Controller
@RequestMapping("m/system/config/history")
@Api(tags="应用系统配置历史表")
@ApiSort(25271718)
public class SystemConfigHistoryController  {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       m/system/config/history/page        权限：m:system:config:history:page
     * 数据列表    m/system/config/history/queryList   权限：m:system:config:history:queryList
     * 根据id查询  m/system/config/history/selectById  权限：m:system:config:history:selectById
     * 新增       m/system/config/history/insert      权限：m:system:config:history:insert
     * 修改       m/system/config/history/updateById  权限：m:system:config:history:updateById
     * 根据id删除  m/system/config/history/deleteById  权限：m:system:config:history:deleteById
     * 多个id删除  m/system/config/history/deleteByIds 权限：m:system:config:history:deleteByIds
     */


    @Autowired
    public SystemConfigHistoryService service;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "/m/systemConfigHistory/index";
    }

    @MarsPermission(":view")
    @GetMapping("/view")
    public String view(){
        return "/m/systemConfigHistory/view";
    }

    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(SystemConfigHistoryReq req) {
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
    public RespVo add(@RequestBody SystemConfigHistoryEntity entity){
        service.save(entity);
        return RespVo.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody SystemConfigHistoryEntity entity){
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


    @MarsPermission(":rollback")
    @ApiOperation("回滚操作")
    @PostMapping("/rollback")
    @ResponseBody
    public RespVo rollback(Long id){
        service.rollback(id);
        return RespVo.success();
    }



}