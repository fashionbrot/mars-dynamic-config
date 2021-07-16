package com.github.fashionbrot.controller;

import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SystemConfigEntity;
import com.github.fashionbrot.req.SystemConfigReq;
import com.github.fashionbrot.service.SystemConfigService;
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
 * 应用系统配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-12
 */

@MarsPermission(value="m:system:config")
@Controller
@RequestMapping("m/system/config")
@Api(tags="应用系统配置表")
@ApiSort(25214010)
public class SystemConfigController {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       m/system/config/page        权限：m:system:config:page
     * 数据列表    m/system/config/queryList   权限：m:system:config:queryList
     * 根据id查询  m/system/config/selectById  权限：m:system:config:selectById
     * 新增       m/system/config/insert      权限：m:system:config:insert
     * 修改       m/system/config/updateById  权限：m:system:config:updateById
     * 根据id删除  m/system/config/deleteById  权限：m:system:config:deleteById
     * 多个id删除  m/system/config/deleteByIds 权限：m:system:config:deleteByIds
     */


    @Autowired
    public SystemConfigService service;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "/m/systemConfig/index";
    }

    @GetMapping("/add")
    public String add(){
        return "/m/systemConfig/add";
    }

    @GetMapping("/edit")
    public String edit(){
        return "/m/systemConfig/edit";
    }

    @GetMapping("/clone")
    public String clone(){
        return "/m/systemConfig/clone";
    }




    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(SystemConfigReq req) {
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
        return RespVo.success(service.selectById(id));
    }

    @MarsPermission(":insert")
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public RespVo add(@RequestBody SystemConfigEntity entity){
        service.add(entity);
        return RespVo.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody SystemConfigEntity entity){
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

    @MarsPermission(":unDel")
    @ApiOperation("撤销删除")
    @PostMapping("/unDel")
    @ResponseBody
    public RespVo undel(Long id){
        service.undel(id);
        return RespVo.success();
    }



    @MarsPermission(":releaseConfig")
    @RequestMapping(value = "releaseConfig")
    @ResponseBody
    public RespVo releaseConfig(SystemConfigEntity req) {
        service.releaseConfig(req);
        return RespVo.success();
    }



}