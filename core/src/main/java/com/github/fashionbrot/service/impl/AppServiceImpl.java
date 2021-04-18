package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.AppEntity;
import com.github.fashionbrot.mapper.AppMapper;
import com.github.fashionbrot.req.AppReq;
import com.github.fashionbrot.service.AppService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 应用表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
public class AppServiceImpl  extends ServiceImpl<AppMapper, AppEntity> implements AppService {

    @Autowired
    private AppMapper appMapper;

    @Override
    public Object pageReq(AppReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<AppEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}