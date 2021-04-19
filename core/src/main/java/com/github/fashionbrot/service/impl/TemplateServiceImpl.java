package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.TemplateEntity;
import com.github.fashionbrot.mapper.TemplateMapper;
import com.github.fashionbrot.req.TemplateReq;
import com.github.fashionbrot.service.TemplateService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
public class TemplateServiceImpl  extends ServiceImpl<TemplateMapper, TemplateEntity> implements TemplateService {



    @Override
    public Object pageReq(TemplateReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(req.getAppCode())){
            map.put("app_code",req.getAppCode());
        }
        List<TemplateEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}