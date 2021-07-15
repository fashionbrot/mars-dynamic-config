package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SystemConfigHistoryEntity;
import com.github.fashionbrot.req.SystemConfigHistoryReq;

/**
 * 应用系统配置历史表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */
public interface SystemConfigHistoryService  extends IService<SystemConfigHistoryEntity> {

    Object pageReq(SystemConfigHistoryReq req);

}