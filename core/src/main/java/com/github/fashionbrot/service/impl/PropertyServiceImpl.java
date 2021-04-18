package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.PropertyEntity;
import com.github.fashionbrot.mapper.PropertyMapper;
import com.github.fashionbrot.req.PropertyReq;
import com.github.fashionbrot.service.PropertyService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public Object pageReq(PropertyReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<PropertyEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}