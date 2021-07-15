package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.SystemReleaseEntity;
import com.github.fashionbrot.mapper.SystemReleaseMapper;
import com.github.fashionbrot.req.SystemReleaseReq;
import com.github.fashionbrot.service.SystemReleaseService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统配置发布表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */
@Service
public class SystemReleaseServiceImpl  extends ServiceImpl<SystemReleaseMapper, SystemReleaseEntity> implements SystemReleaseService {

    @Autowired
    private SystemReleaseMapper systemReleaseMapper;

    @Override
    public Object pageReq(SystemReleaseReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<SystemReleaseEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}