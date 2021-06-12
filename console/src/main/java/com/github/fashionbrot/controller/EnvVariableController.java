package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.EnvVariableEntity;
import com.github.fashionbrot.req.EnvVariableReq;
import com.github.fashionbrot.service.EnvVariableService;
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
 * 常量表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */

@MarsPermission(value="m:env:variable")
@Controller
@RequestMapping("m/env/variable")
@Api(tags="常量表")
@ApiSort(23751667)
public class EnvVariableController  {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       m/env/variable/page        权限：m:env:variable:page
     * 数据列表    m/env/variable/queryList   权限：m:env:variable:queryList
     * 根据id查询  m/env/variable/selectById  权限：m:env:variable:selectById
     * 新增       m/env/variable/insert      权限：m:env:variable:insert
     * 修改       m/env/variable/updateById  权限：m:env:variable:updateById
     * 根据id删除  m/env/variable/deleteById  权限：m:env:variable:deleteById
     * 多个id删除  m/env/variable/deleteByIds 权限：m:env:variable:deleteByIds
     */


    @Autowired
    public EnvVariableService service;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "/m/variable/index";
    }

    @MarsPermission(":add")
    @GetMapping("/add")
    public String add(){
        return "/m/variable/add";
    }

    @MarsPermission(":edit")
    @GetMapping("/edit")
    public String edit(){
        return "/m/variable/edit";
    }


    @MarsPermission(":index")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(EnvVariableReq req) {
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
        return RespVo.success(service.getById(id));
    }


    @MarsLog
    @MarsPermission(":insert")
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public RespVo add(@RequestBody EnvVariableEntity entity){
        service.add(entity);
        return RespVo.success();
    }


    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody EnvVariableEntity entity){
        service.edit(entity);
        return RespVo.success();
    }


    @MarsLog
    @MarsPermission(":deleteById")
    @ApiOperation("根据id删除")
    @PostMapping("/deleteById")
    @ResponseBody
    public RespVo deleteById(Long id){
        service.deleteById(id);
        return RespVo.success();
    }





}