package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.EnvVariableEntity;
import com.github.fashionbrot.req.EnvVariableReq;

/**
 * 常量表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
public interface EnvVariableService  extends IService<EnvVariableEntity> {

    Object pageReq(EnvVariableReq req);

}