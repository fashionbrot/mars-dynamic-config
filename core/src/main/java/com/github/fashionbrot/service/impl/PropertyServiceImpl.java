package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.PropertyEntity;
import com.github.fashionbrot.mapper.PropertyMapper;
import com.github.fashionbrot.req.PropertyReq;
import com.github.fashionbrot.service.PropertyService;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 属性表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
public class PropertyServiceImpl  extends ServiceImpl<PropertyMapper, PropertyEntity> implements PropertyService {



    @Override
    public Object pageReq(PropertyReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        QueryWrapper q =new QueryWrapper();
        if (StringUtils.isNotEmpty(req.getAppCode())){
            q.eq("app_code",req.getAppCode());
            if (StringUtils.isNotEmpty(req.getTemplateKey())){
                q.eq("template_key",req.getTemplateKey());
            }
        }
        q.orderByDesc("priority");

        List<PropertyEntity> listByMap = baseMapper.selectList(q);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Override
    public Object queryList(PropertyReq req) {
        QueryWrapper q =new QueryWrapper();
        if (StringUtils.isNotEmpty(req.getAppCode())){
            q.eq("app_code",req.getAppCode());
            if (StringUtils.isNotEmpty(req.getTemplateKey())){
                q.eq("template_key",req.getTemplateKey());
            }
        }
        if (req.getShowTable()!=null){
            q.eq("show_table",req.getShowTable());
        }
        q.orderByDesc("priority");
        return baseMapper.selectList(q);
    }

}