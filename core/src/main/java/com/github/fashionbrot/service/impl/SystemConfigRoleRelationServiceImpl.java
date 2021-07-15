package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.SystemConfigRoleRelationEntity;
import com.github.fashionbrot.mapper.SystemConfigRoleRelationMapper;
import com.github.fashionbrot.req.SystemConfigRoleRelationReq;
import com.github.fashionbrot.service.SystemConfigRoleRelationService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 应用系统配置-角色关系表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */
@Service
public class SystemConfigRoleRelationServiceImpl  extends ServiceImpl<SystemConfigRoleRelationMapper, SystemConfigRoleRelationEntity> implements SystemConfigRoleRelationService {

    @Autowired
    private SystemConfigRoleRelationMapper systemConfigRoleRelationMapper;

    @Override
    public Object pageReq(SystemConfigRoleRelationReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<SystemConfigRoleRelationEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}