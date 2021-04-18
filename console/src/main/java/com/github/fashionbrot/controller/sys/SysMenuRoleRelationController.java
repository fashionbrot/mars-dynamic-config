package com.github.fashionbrot.controller.sys;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SysMenuRoleRelationEntity;
import com.github.fashionbrot.req.SysMenuRoleRelationReq;
import com.github.fashionbrot.service.SysMenuRoleRelationService;
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
 * 菜单-角色关系表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */

@MarsPermission(value="sys:menu:role:relation")
@Controller
@RequestMapping("sys/menu/role/relation")
@Api(tags="菜单-角色关系表")
@ApiSort(23744272)
public class SysMenuRoleRelationController  {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       sys/menu/role/relation/page        权限：sys:menu:role:relation:page
     * 数据列表    sys/menu/role/relation/queryList   权限：sys:menu:role:relation:queryList
     * 根据id查询  sys/menu/role/relation/selectById  权限：sys:menu:role:relation:selectById
     * 新增       sys/menu/role/relation/insert      权限：sys:menu:role:relation:insert
     * 修改       sys/menu/role/relation/updateById  权限：sys:menu:role:relation:updateById
     * 根据id删除  sys/menu/role/relation/deleteById  权限：sys:menu:role:relation:deleteById
     * 多个id删除  sys/menu/role/relation/deleteByIds 权限：sys:menu:role:relation:deleteByIds
     */


    @Autowired
    public SysMenuRoleRelationService service;



    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public RespVo pageReq(SysMenuRoleRelationReq req) {
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

    @MarsLog
    @MarsPermission(":insert")
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public RespVo add(@RequestBody SysMenuRoleRelationEntity entity){
        service.save(entity);
        return RespVo.success();
    }


    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public RespVo updateById(@RequestBody SysMenuRoleRelationEntity entity){
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


    @MarsLog
    @MarsPermission(":deleteByIds")
    @ApiOperation("批量删除")
    @PostMapping("/deleteByIds")
    @ResponseBody
    public RespVo delete(@RequestBody Long[] ids){
        service.removeByIds(Arrays.asList(ids));
        return RespVo.success();
    }



}