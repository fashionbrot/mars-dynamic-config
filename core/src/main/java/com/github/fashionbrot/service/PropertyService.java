package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.PropertyEntity;
import com.github.fashionbrot.req.PropertyReq;

import java.util.Map;

/**
 * 属性表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
public interface PropertyService  extends IService<PropertyEntity> {

    Object pageReq(PropertyReq req);

    Object queryList(PropertyReq params);
}