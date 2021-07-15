package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.SystemConfigHistoryEntity;
import com.github.fashionbrot.mapper.SystemConfigHistoryMapper;
import com.github.fashionbrot.req.SystemConfigHistoryReq;
import com.github.fashionbrot.service.SystemConfigHistoryService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 应用系统配置历史表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */
@Service
public class SystemConfigHistoryServiceImpl  extends ServiceImpl<SystemConfigHistoryMapper, SystemConfigHistoryEntity> implements SystemConfigHistoryService {

    @Autowired
    private SystemConfigHistoryMapper systemConfigHistoryMapper;

    @Override
    public Object pageReq(SystemConfigHistoryReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<SystemConfigHistoryEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}