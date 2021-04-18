package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SysMenuRoleRelationEntity;
import com.github.fashionbrot.req.SysMenuRoleRelationReq;

/**
 * 菜单-角色关系表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
public interface SysMenuRoleRelationService  extends IService<SysMenuRoleRelationEntity> {

    Object pageReq(SysMenuRoleRelationReq req);

}