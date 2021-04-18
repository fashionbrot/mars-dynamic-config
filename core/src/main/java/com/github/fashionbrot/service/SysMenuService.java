package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SysMenuEntity;
import com.github.fashionbrot.req.SysMenuReq;

/**
 * 菜单表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
public interface SysMenuService  extends IService<SysMenuEntity> {

    Object pageReq(SysMenuReq req);

}