package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.TemplateEntity;
import com.github.fashionbrot.req.TemplateReq;
import com.github.fashionbrot.service.TemplateService;
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
 * 模板表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */

@MarsPermission(value="m:template")
@Controller
@RequestMapping("m/template")
@Api(tags="模板表")
@ApiSort(23751667)
public class TemplateController  {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       m/template/page        权限：m:template:page
     * 数据列表    m/template/queryList   权限：m:template:queryList
     * 根据id查询  m/template/selectById  权限：m:template:selectById
     * 新增       m/template/insert      权限：m:template:insert
     * 修改       m/template/updateById  权限：m:template:updateById
     * 根据id删除  m/template/deleteById  权限：m:template:deleteById
     * 多个id删除  m/template/deleteByIds 权限：m:template:deleteByIds
     */


    @Autowired
    public TemplateService service;


    @GetMapping("/index")
    public String index(){
        return "/m/template/index";
    }

    @GetMapping("/add")
    public String add(){
        return "/m/template/add";
    }

    @GetMapping("/edit")
    public String edit(){
        return "/m/template/edit";
    }


    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(TemplateReq req) {
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
    public RespVo add(@RequestBody TemplateEntity entity){
        service.save(entity);
        return RespVo.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody TemplateEntity entity){
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