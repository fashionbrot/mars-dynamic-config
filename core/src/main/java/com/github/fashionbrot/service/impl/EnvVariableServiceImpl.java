package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.EnvVariableEntity;
import com.github.fashionbrot.entity.EnvVariableRelationEntity;
import com.github.fashionbrot.exception.MarsException;
import com.github.fashionbrot.mapper.EnvVariableMapper;
import com.github.fashionbrot.mapper.EnvVariableRelationMapper;
import com.github.fashionbrot.req.EnvVariableReq;
import com.github.fashionbrot.service.EnvVariableService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 常量表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
public class EnvVariableServiceImpl  extends ServiceImpl<EnvVariableMapper, EnvVariableEntity> implements EnvVariableService {


    @Autowired
    private EnvVariableRelationMapper envVariableRelationMapper;

    @Override
    public Object pageReq(EnvVariableReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<EnvVariableEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(EnvVariableEntity entity) {
        Integer count = baseMapper.selectCount(new QueryWrapper<EnvVariableEntity>().eq("variable_key", entity.getVariableKey()));
        if (count>0){
            throw new MarsException(entity.getVariableKey()+"：已存在，请重新输入");
        }

        baseMapper.insert(entity);
        List<EnvVariableRelationEntity> relation = entity.getRelation();
        if (CollectionUtils.isNotEmpty(relation)){
            for(EnvVariableRelationEntity e :relation){
                envVariableRelationMapper.insert(e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(EnvVariableEntity entity) {
        EnvVariableEntity variable_key = baseMapper.selectOne(new QueryWrapper<EnvVariableEntity>().eq("variable_key", entity.getVariableKey()));
        if (variable_key!=null && variable_key.getId().longValue()!=entity.getId().longValue()){
            throw new MarsException(entity.getVariableKey()+"：已存在，请重新输入");
        }

        baseMapper.updateById(entity);
        envVariableRelationMapper.delete(new QueryWrapper<EnvVariableRelationEntity>().eq("variable_key",entity.getVariableKey()));
        List<EnvVariableRelationEntity> relation = entity.getRelation();
        if (CollectionUtils.isNotEmpty(relation)){
            for(EnvVariableRelationEntity e :relation){
                envVariableRelationMapper.insert(e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        EnvVariableEntity entity = baseMapper.selectById(id);
        if (entity!=null){
            //TODO 判断是否已使用，已使用不能删除
            baseMapper.deleteById(id);
            envVariableRelationMapper.delete(new QueryWrapper<EnvVariableRelationEntity>().eq("variable_key",entity.getVariableKey()));
        }

    }
}