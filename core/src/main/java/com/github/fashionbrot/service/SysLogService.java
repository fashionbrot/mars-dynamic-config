package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SysLogEntity;
import com.github.fashionbrot.req.SysLogReq;

/**
 * 系统日志
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
public interface SysLogService extends IService<SysLogEntity> {

    Object pageReq(SysLogReq req);

}