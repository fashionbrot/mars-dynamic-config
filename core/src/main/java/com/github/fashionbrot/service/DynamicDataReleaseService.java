package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.DynamicDataReleaseEntity;
import com.github.fashionbrot.req.DynamicDataReleaseReq;

/**
 * 配置数据发布表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
public interface DynamicDataReleaseService  extends IService<DynamicDataReleaseEntity> {

    Object pageReq(DynamicDataReleaseReq req);

}