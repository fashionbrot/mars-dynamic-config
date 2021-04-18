package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.TemplateEntity;
import com.github.fashionbrot.req.TemplateReq;

/**
 * 模板表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
public interface TemplateService  extends IService<TemplateEntity> {

    Object pageReq(TemplateReq req);

}