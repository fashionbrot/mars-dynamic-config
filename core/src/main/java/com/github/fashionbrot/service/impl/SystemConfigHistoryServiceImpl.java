package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.SystemConfigEntity;
import com.github.fashionbrot.entity.SystemConfigHistoryEntity;
import com.github.fashionbrot.exception.MarsException;
import com.github.fashionbrot.mapper.SystemConfigHistoryMapper;
import com.github.fashionbrot.mapper.SystemConfigMapper;
import com.github.fashionbrot.req.SystemConfigHistoryReq;
import com.github.fashionbrot.service.SystemConfigHistoryService;
import com.github.fashionbrot.service.SystemConfigService;
import com.github.fashionbrot.util.StringUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 应用系统配置历史表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */
@SuppressWarnings("ALL")
@Service
public class SystemConfigHistoryServiceImpl  extends ServiceImpl<SystemConfigHistoryMapper, SystemConfigHistoryEntity> implements SystemConfigHistoryService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    public Object pageReq(SystemConfigHistoryReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        QueryWrapper q=new QueryWrapper();
        q.select("        id,app_code,env_code,modifier,file_name,file_desc,file_type,status,create_date,update_date ");
        if (StringUtil.isNotEmpty(req.getEnvCode())){
            q.eq("env_code",req.getEnvCode());
        }
        if (StringUtil.isNotEmpty(req.getAppCode())){
            q.eq("app_code",req.getAppCode());
        }
        List<SystemConfigHistoryEntity> listByMap = baseMapper.selectList(q);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rollback(Long id) {
        SystemConfigHistoryEntity historyEntity = baseMapper.selectById(id);
        if (historyEntity!=null){
            QueryWrapper q=new QueryWrapper();
            q.eq("env_code",historyEntity.getEnvCode());
            q.eq("app_code",historyEntity.getAppCode());
            q.eq("file_name",historyEntity.getFileName());
            SystemConfigEntity systemConfigEntity = systemConfigMapper.selectOne(q);
            if (systemConfigEntity==null){
                throw new MarsException("要回滚的配置不存在或已删除");
            }
            String json = StringUtil.isNotEmpty(systemConfigEntity.getJson())?systemConfigEntity.getJson():"";

            if (!json.equals(historyEntity.getJson())){
                systemConfigEntity.setTempJson(historyEntity.getJson());
                systemConfigEntity.setStatus(5);
                systemConfigMapper.updateById(systemConfigEntity);
                systemConfigService.updateRelease(historyEntity.getEnvCode(),historyEntity.getAppCode(),historyEntity.getFileName());
            }

        }
    }

}