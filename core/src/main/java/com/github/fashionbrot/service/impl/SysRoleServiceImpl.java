package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.SysMenuRoleRelationEntity;
import com.github.fashionbrot.entity.SysRoleEntity;
import com.github.fashionbrot.mapper.SysMenuRoleRelationMapper;
import com.github.fashionbrot.mapper.SysRoleMapper;
import com.github.fashionbrot.req.SysRoleReq;
import com.github.fashionbrot.service.SysRoleService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 角色表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
public class SysRoleServiceImpl  extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    @Autowired
    private SysMenuRoleRelationMapper sysMenuRoleRelationMapper;


    @Override
    public Object pageReq(SysRoleReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<SysRoleEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysRoleEntity entity) {
        baseMapper.insert(entity);

        if (StringUtils.isNotEmpty(entity.getMenuIds())) {
            String[] menuIds = entity.getMenuIds().split(",");
            for (String menuId : menuIds) {
                sysMenuRoleRelationMapper.insert(SysMenuRoleRelationEntity.builder()
                        .roleId(entity.getId())
                        .menuId(Long.valueOf(menuId))
                        .build());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(SysRoleEntity entity) {
        baseMapper.updateById(entity);
        sysMenuRoleRelationMapper.delete(new QueryWrapper<SysMenuRoleRelationEntity>().eq("role_id", entity.getId()));
        if (StringUtils.isNotEmpty(entity.getMenuIds())) {
            String[] menuIds = entity.getMenuIds().split(",");
            for (String menuId : menuIds) {
                sysMenuRoleRelationMapper.insert(SysMenuRoleRelationEntity.builder()
                        .roleId(entity.getId())
                        .menuId(Long.valueOf(menuId))
                        .build());
            }
        }
    }

}