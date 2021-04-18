package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SysRoleEntity;
import com.github.fashionbrot.req.SysRoleReq;

/**
 * 角色表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
public interface SysRoleService  extends IService<SysRoleEntity> {

    Object pageReq(SysRoleReq req);

    void add(SysRoleEntity entity);

    void edit(SysRoleEntity entity);
}