package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.DynamicDataValueEntity;
import com.github.fashionbrot.mapper.DynamicDataValueMapper;
import com.github.fashionbrot.req.DynamicDataValueReq;
import com.github.fashionbrot.service.DynamicDataValueService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 动态配置数据表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Service
public class DynamicDataValueServiceImpl  extends ServiceImpl<DynamicDataValueMapper, DynamicDataValueEntity> implements DynamicDataValueService {

    @Autowired
    private DynamicDataValueMapper dynamicDataValueMapper;

    @Override
    public Object pageReq(DynamicDataValueReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<DynamicDataValueEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}