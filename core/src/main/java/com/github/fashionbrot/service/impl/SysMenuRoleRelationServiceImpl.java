package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.SysMenuRoleRelationEntity;
import com.github.fashionbrot.mapper.SysMenuRoleRelationMapper;
import com.github.fashionbrot.req.SysMenuRoleRelationReq;
import com.github.fashionbrot.service.SysMenuRoleRelationService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 菜单-角色关系表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
public class SysMenuRoleRelationServiceImpl  extends ServiceImpl<SysMenuRoleRelationMapper, SysMenuRoleRelationEntity> implements SysMenuRoleRelationService {

    @Autowired
    private SysMenuRoleRelationMapper sysMenuRoleRelationMapper;

    @Override
    public Object pageReq(SysMenuRoleRelationReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<SysMenuRoleRelationEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

}