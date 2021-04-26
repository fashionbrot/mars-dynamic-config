package com.github.fashionbrot.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.DynamicDataEntity;
import com.github.fashionbrot.entity.DynamicDataLogEntity;
import com.github.fashionbrot.entity.DynamicDataValueEntity;
import com.github.fashionbrot.enums.OperationTypeEnum;
import com.github.fashionbrot.enums.ReleaseTypeEnum;
import com.github.fashionbrot.mapper.DynamicDataLogMapper;
import com.github.fashionbrot.mapper.DynamicDataMapper;
import com.github.fashionbrot.mapper.DynamicDataValueMapper;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.req.DynamicDataReq;
import com.github.fashionbrot.service.DynamicDataService;
import com.github.fashionbrot.service.UserLoginService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Service
public class DynamicDataServiceImpl  extends ServiceImpl<DynamicDataMapper, DynamicDataEntity> implements DynamicDataService {

    @Autowired
    private DynamicDataValueMapper dynamicDataValueMapper;
    @Autowired
    private DynamicDataLogMapper dynamicDataLogMapper;
    @Autowired
    private UserLoginService userLoginService;

    @Override
    public Object pageReq(DynamicDataReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<Map<String, Object>> listByMap = baseMapper.pageReq2(req);

        if (CollectionUtils.isNotEmpty(listByMap)){
            for (Map<String, Object> map : listByMap) {
                Object releaseType = map.get("releaseType");

                if ((ReleaseTypeEnum.RELEASE.getCode()+"").equals(releaseType+"")){
                    if (map.containsKey("json")){
                        String json = (String)map.get("json");
                        map.putAll(JSON.parseObject(json));
                    }
                }else{
                    if (map.containsKey("tempJson")){
                        String json = (String)map.get("tempJson");
                        map.putAll(JSON.parseObject(json));
                    }
                }
            }
        }

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Override
    public Object selectById(Long id) {
        DynamicDataEntity entity = baseMapper.selectById(id);
        DynamicDataValueEntity data = dynamicDataValueMapper.selectOne(new QueryWrapper<DynamicDataValueEntity>().eq("data_id", id));
        if (data!=null){
            if (entity.getReleaseType() == ReleaseTypeEnum.RELEASE.getCode()){
                entity.setJson(data.getJson());
                entity.setTempJson(data.getTempJson());
            }else{
                entity.setJson(data.getTempJson());
                entity.setTempJson(data.getJson());
            }
        }

        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(DynamicDataEntity entity) {
        LoginModel login = userLoginService.getLogin();
        entity.setUpdateId(login.getUserId());
        entity.setUpdateDate(new Date());

        entity.setReleaseType(ReleaseTypeEnum.ADD.getCode());
        baseMapper.insert(entity);
        dynamicDataValueMapper.insert(DynamicDataValueEntity.builder()
                .dataId(entity.getId())
                .tempJson(entity.getTempJson())
                .build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(DynamicDataEntity entity) {

        entity.setReleaseType(ReleaseTypeEnum.UPDATE.getCode());
        baseMapper.updateById(entity);
        DynamicDataValueEntity data = dynamicDataValueMapper.selectOne(new QueryWrapper<DynamicDataValueEntity>().eq("data_id", entity.getId()));
        if (data!=null){
            data.setTempJson(entity.getTempJson());
            dynamicDataValueMapper.updateById(data);
        }


        DynamicDataLogEntity log= DynamicDataLogEntity.builder().build();
        BeanUtils.copyProperties(entity,log);
        log.setOperationType(OperationTypeEnum.EDIT.getCode());
        log.setDescription(entity.getDataDesc());
        log.setJson(data.getTempJson());
        log.setTempJson(data.getJson());
        log.setDataValueId(data.getId());
        dynamicDataLogMapper.insert(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {

        DynamicDataEntity entity = baseMapper.selectById(id);
        DynamicDataValueEntity data = dynamicDataValueMapper.selectOne(new QueryWrapper<DynamicDataValueEntity>().eq("data_id", entity.getId()));

        DynamicDataLogEntity log= DynamicDataLogEntity.builder().build();
        BeanUtils.copyProperties(entity,log);
        log.setOperationType(OperationTypeEnum.DEL.getCode());
        log.setDescription(entity.getDataDesc());
        log.setJson(data.getJson());
        log.setTempJson("");
        log.setDataValueId(data.getId());
        dynamicDataLogMapper.insert(log);

        baseMapper.deleteById(id);
        dynamicDataValueMapper.delete(new QueryWrapper<DynamicDataValueEntity>().eq("data_id", id));
    }
}