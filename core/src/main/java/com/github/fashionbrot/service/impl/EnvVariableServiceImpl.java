package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.EnvVariableEntity;
import com.github.fashionbrot.mapper.EnvVariableMapper;
import com.github.fashionbrot.req.EnvVariableReq;
import com.github.fashionbrot.service.EnvVariableService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 常量表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
public class EnvVariableServiceImpl  extends ServiceImpl<EnvVariableMapper, EnvVariableEntity> implements EnvVariableService {

    @Autowired
    private EnvVariableMapper envVariableMapper;

    @Override
    public Object pageReq(EnvVariableReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<EnvVariableEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}