package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SystemConfigRoleRelationEntity;
import com.github.fashionbrot.req.SystemConfigRoleRelationReq;

/**
 * 应用系统配置-角色关系表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */
public interface SystemConfigRoleRelationService  extends IService<SystemConfigRoleRelationEntity> {

    Object pageReq(SystemConfigRoleRelationReq req);

}