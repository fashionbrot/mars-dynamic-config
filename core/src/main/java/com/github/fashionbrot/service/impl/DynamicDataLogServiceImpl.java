package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.DynamicDataLogEntity;
import com.github.fashionbrot.mapper.DynamicDataLogMapper;
import com.github.fashionbrot.req.DynamicDataLogReq;
import com.github.fashionbrot.service.DynamicDataLogService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 配置数据记录表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Service
public class DynamicDataLogServiceImpl  extends ServiceImpl<DynamicDataLogMapper, DynamicDataLogEntity> implements DynamicDataLogService {

    @Autowired
    private DynamicDataLogMapper dynamicDataLogMapper;

    @Override
    public Object pageReq(DynamicDataLogReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<DynamicDataLogEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}