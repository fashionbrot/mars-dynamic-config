package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SystemReleaseEntity;
import com.github.fashionbrot.req.SystemReleaseReq;

/**
 * 系统配置发布表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */
public interface SystemReleaseService  extends IService<SystemReleaseEntity> {

    Object pageReq(SystemReleaseReq req);

}