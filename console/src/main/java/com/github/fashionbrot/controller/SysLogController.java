package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SysLogEntity;
import com.github.fashionbrot.req.SysLogReq;
import com.github.fashionbrot.service.SysLogService;
import com.github.fashionbrot.vo.RespVo;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 系统日志
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */

@MarsPermission(value="sys:log")
@Controller
@RequestMapping("sys/log")
@Api(tags="系统日志")
@ApiSort(23735114)
public class SysLogController {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       sys/log/page        权限：sys:log:page
     * 数据列表    sys/log/queryList   权限：sys:log:queryList
     * 根据id查询  sys/log/selectById  权限：sys:log:selectById
     * 新增       sys/log/insert      权限：sys:log:insert
     * 修改       sys/log/updateById  权限：sys:log:updateById
     * 根据id删除  sys/log/deleteById  权限：sys:log:deleteById
     * 多个id删除  sys/log/deleteByIds 权限：sys:log:deleteByIds
     */


    @Autowired
    public SysLogService service;


    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "system/log/log";
    }


    @MarsPermission(":index:detail")
    @GetMapping("/index/detail")
    public String detail( Long id, ModelMap modelMap){
        SysLogEntity data = service.getById(id);
        modelMap.put("operLog",data);
        return "system/log/detail";
    }





    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(SysLogReq req) {
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
    public RespVo add(@RequestBody SysLogEntity entity){
        service.save(entity);
        return RespVo.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody SysLogEntity entity){
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