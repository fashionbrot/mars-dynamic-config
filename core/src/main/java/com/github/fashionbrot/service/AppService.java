package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.AppEntity;
import com.github.fashionbrot.req.AppReq;

/**
 * 应用表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
public interface AppService  extends IService<AppEntity> {

    Object pageReq(AppReq req);

}