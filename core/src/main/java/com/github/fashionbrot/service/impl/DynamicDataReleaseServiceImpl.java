package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.DynamicDataReleaseEntity;
import com.github.fashionbrot.mapper.DynamicDataReleaseMapper;
import com.github.fashionbrot.req.DynamicDataReleaseReq;
import com.github.fashionbrot.service.DynamicDataReleaseService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 配置数据发布表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Service
public class DynamicDataReleaseServiceImpl  extends ServiceImpl<DynamicDataReleaseMapper, DynamicDataReleaseEntity> implements DynamicDataReleaseService {

    @Autowired
    private DynamicDataReleaseMapper dynamicDataReleaseMapper;

    @Override
    public Object pageReq(DynamicDataReleaseReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<DynamicDataReleaseEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}