package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.SystemConfigEntity;
import com.github.fashionbrot.exception.MarsException;
import com.github.fashionbrot.mapper.SystemConfigMapper;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.req.SystemConfigReq;
import com.github.fashionbrot.service.SystemConfigService;
import com.github.fashionbrot.service.UserLoginService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 应用系统配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-12
 */
@Service
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfigEntity> implements SystemConfigService {


    @Autowired
    private UserLoginService userLoginService;

    @Override
    public Object pageReq(SystemConfigReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<SystemConfigEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Override
    public void add(SystemConfigEntity entity) {
        QueryWrapper q=new QueryWrapper();
        q.eq("env_code",entity.getEnvCode());
        q.eq("app_code",entity.getAppCode());
        q.eq("file_name",entity.getFileName());
        if (baseMapper.selectCount(q)>0){
            throw new MarsException(entity.getFileName()+"已存在，请重新填写");
        }
        LoginModel login = userLoginService.getLogin();
        entity.setStatus(1);
        entity.setModifier(login.getUserName());
        baseMapper.insert(entity);
    }

    @Override
    public void edit(SystemConfigEntity entity) {
        QueryWrapper q=new QueryWrapper();
        q.eq("env_code",entity.getEnvCode());
        q.eq("app_code",entity.getAppCode());
        q.eq("file_name",entity.getFileName());
        SystemConfigEntity systemConfigEntity = baseMapper.selectOne(q);
        if (systemConfigEntity!=null && systemConfigEntity.getId().longValue()!=systemConfigEntity.getId().longValue()){
            throw new MarsException(entity.getFileName()+"已存在，请重新填写");
        }
        LoginModel login = userLoginService.getLogin();
        entity.setStatus(2);
        entity.setModifier(login.getUserName());
        baseMapper.updateById(entity);
    }

    @Override
    public void deleteById(Long id) {
        SystemConfigEntity entity = baseMapper.selectById(id);
        if (entity.getStatus()==1 ){
            baseMapper.deleteById(id);
            return;
        }
        LoginModel login = userLoginService.getLogin();
        entity.setStatus(3);
        entity.setModifier(login.getUserName());
        baseMapper.updateById(entity);
    }

    @Override
    public Object selectById(Long id) {
        SystemConfigEntity entity = baseMapper.selectById(id);
        if (entity!=null){
            if (entity.getStatus()==1 || entity.getStatus()==2){
                entity.setJson(entity.getTempJson());
            }
        }
        return entity;
    }

    @Override
    public void undel(Long id) {
        SystemConfigEntity entity = baseMapper.selectById(id);
        entity.setStatus(4);
        baseMapper.updateById(entity);
    }
}