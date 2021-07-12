package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SystemConfigEntity;
import com.github.fashionbrot.req.SystemConfigReq;

/**
 * 应用系统配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-12
 */
public interface SystemConfigService extends IService<SystemConfigEntity> {

    Object pageReq(SystemConfigReq req);

}