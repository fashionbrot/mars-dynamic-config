package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.EnvEntity;
import com.github.fashionbrot.req.EnvReq;
import com.github.fashionbrot.service.EnvService;
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
 * 环境表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */

@MarsPermission(value="m:env")
@Controller
@RequestMapping("m/env")
@Api(tags="环境表")
@ApiSort(23751667)
public class EnvController  {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       m/env/page        权限：m:env:page
     * 数据列表    m/env/queryList   权限：m:env:queryList
     * 根据id查询  m/env/selectById  权限：m:env:selectById
     * 新增       m/env/insert      权限：m:env:insert
     * 修改       m/env/updateById  权限：m:env:updateById
     * 根据id删除  m/env/deleteById  权限：m:env:deleteById
     * 多个id删除  m/env/deleteByIds 权限：m:env:deleteByIds
     */


    @Autowired
    public EnvService service;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "/m/env/index";
    }

    @MarsPermission(":add")
    @GetMapping("/add")
    public String add(){
        return "/m/env/add";
    }

    @MarsPermission(":edit")
    @GetMapping("/edit")
    public String edit(){
        return "/m/env/edit";
    }



    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(EnvReq req) {
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
    public RespVo add(@RequestBody EnvEntity entity){
        service.save(entity);
        return RespVo.success();
    }


    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody EnvEntity entity){
        service.updateById(entity);
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