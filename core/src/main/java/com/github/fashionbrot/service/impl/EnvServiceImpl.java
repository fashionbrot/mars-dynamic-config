package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.EnvEntity;
import com.github.fashionbrot.mapper.EnvMapper;
import com.github.fashionbrot.req.EnvReq;
import com.github.fashionbrot.service.EnvService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 环境表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
public class EnvServiceImpl  extends ServiceImpl<EnvMapper, EnvEntity> implements EnvService {

    @Autowired
    private EnvMapper envMapper;

    @Override
    public Object pageReq(EnvReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<EnvEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}