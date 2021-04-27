package com.github.fashionbrot.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.dto.DataDynamicJsonDTO;
import com.github.fashionbrot.entity.*;
import com.github.fashionbrot.enums.OperationTypeEnum;
import com.github.fashionbrot.enums.ReleaseTypeEnum;
import com.github.fashionbrot.exception.CurdException;
import com.github.fashionbrot.exception.MarsException;
import com.github.fashionbrot.mapper.*;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.req.DataDynamicApiReq;
import com.github.fashionbrot.req.DynamicDataReq;
import com.github.fashionbrot.service.DynamicDataService;
import com.github.fashionbrot.service.UserLoginService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.validated.util.StringUtil;
import com.github.fashionbrot.vo.ApiRespVo;
import com.github.fashionbrot.vo.DataDynamicJsonVo;
import com.github.fashionbrot.vo.DataDynamicVo;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 动态配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@SuppressWarnings("ALL")
@Service
@Slf4j
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
        log.setJson(StringUtil.isNotEmpty(data.getJson())?data.getJson():data.getTempJson());
        log.setTempJson("");
        log.setDataValueId(data.getId());
        dynamicDataLogMapper.insert(log);

        baseMapper.deleteById(id);
        dynamicDataValueMapper.delete(new QueryWrapper<DynamicDataValueEntity>().eq("data_id", id));
    }


    @Autowired
    private DynamicDataReleaseMapper dynamicDataReleaseMapper;
    @Autowired
    private EnvVariableRelationMapper envVariableRelationMapper;

    private String getKey(String env,String app){
        return env+app;
    }

    private Map<String,Long> versionCache = new ConcurrentHashMap<>();

    public void setVersionCache(String envCode,String appCode,Long releaseId){
        String key =  getKey(envCode,appCode);
        versionCache.put(key,releaseId);
    }


    @Override
    public Object checkVersion(DataDynamicApiReq req) {
        String key =  getKey(req.getEnvCode(),req.getAppCode());
        if (versionCache.containsKey(key)){
            return versionCache.get(key);
        }

        Long version = dynamicDataReleaseMapper.getTopReleaseId(req.getEnvCode(),req.getAppCode());
        if (version==null){
            versionCache.put(key,-1L);
            return -1;
        }else{
            versionCache.put(key,version);
            return version;
        }
    }

    @Override
    public ApiRespVo getData(DataDynamicApiReq req) {

        String key = getKey(req.getEnvCode(), req.getAppCode());

        List<EnvVariableRelationEntity> variableRelationList = envVariableRelationMapper.selectList(null);

        if ("1".equals(req.getAll())) {

            Map<String, Object> map = new HashMap<>();
            map.put("envCode", req.getEnvCode());
            map.put("appCode", req.getAppCode());
            if (StringUtil.isNotEmpty(req.getTemplateKeys())){
                List<String> keyList = Arrays.stream(req.getTemplateKeys().split(",")).collect(Collectors.toList());
                map.put("templateKeyList", keyList);
            }

            List<DataDynamicVo> dynamicDataList = baseMapper.selectJsonByReq(map);
            if (CollectionUtils.isNotEmpty(dynamicDataList)){
                dynamicDataList = dynamicDataList
                        .stream()
                        .map(m-> DataDynamicVo.builder()
                                .templateKey(m.getTemplateKey())
                                .json(format(m.getJsonList(), req.getEnvCode(),variableRelationList))
                                .build())
                        .collect(Collectors.toList());
            }

            return ApiRespVo.builder()
                    .code(0)
                    .version(versionCache.get(key))
                    .data(dynamicDataList)
                    .build();

        } else {
            if (req.getVersion() != null && req.getVersion().longValue() == -1L) {
                return ApiRespVo.builder().code(1).msg("应用未配置信息").build();
            }
            DynamicDataReleaseEntity release = dynamicDataReleaseMapper.selectById (req.getVersion());
            if (release == null) {
                return ApiRespVo.builder().code(1).msg("应用未配置信息").build();
            }
            if (StringUtils.isEmpty(release.getTemplateKeys())) {
                return ApiRespVo.builder().code(0).build();
            }

            if (!versionCache.containsKey(key)) {
                return ApiRespVo.builder().code(1).msg("应用未配置信息").build();
            }


            Map<String, Object> map = new HashMap<>();
            map.put("envCode", release.getEnvCode());
            map.put("appName", release.getAppCode());
            String keys = release.getTemplateKeys();
            if (StringUtil.isNotEmpty(req.getTemplateKeys())){
                keys = req.getTemplateKeys();
            }
            List<String> keyList = Arrays.stream(keys.split(",")).collect(Collectors.toList());
            map.put("templateKeyList", keyList);

            List<DataDynamicVo> dynamicDataList = baseMapper.selectJsonByReq(map);

            if (CollectionUtils.isNotEmpty(dynamicDataList)){
                dynamicDataList = dynamicDataList
                        .stream()
                        .map(m-> DataDynamicVo.builder()
                                .templateKey(m.getTemplateKey())
                                .json(format(m.getJsonList(), req.getEnvCode(),variableRelationList))
                                .build())
                        .collect(Collectors.toList());
            }

            return ApiRespVo.builder()
                    .code(0)
                    .version(versionCache.get(key))
                    .data(dynamicDataList)
                    .build();
        }

    }

    private List<JSONObject> format(List<DataDynamicJsonVo> jsonVos, String envCode, List<EnvVariableRelationEntity> variableRelationList){
        if (CollectionUtils.isNotEmpty(jsonVos)){
            return jsonVos.stream().map(m-> formatVariable(m.getJson(),envCode,variableRelationList) ).collect(Collectors.toList());
        }
        return null;
    }
    private JSONObject formatVariable(String json,String envCode,List<EnvVariableRelationEntity> variableRelationList){

        if (StringUtils.isNotEmpty(json)){
            try {
                JSONObject map= JSONObject.parseObject(json);
                if (CollectionUtils.isNotEmpty(map)){
                    map.forEach((k,v)->{
                        String keyPrefix=k+"Variable";
                        if (map.containsKey(keyPrefix)){
                            String valuePrefix= (String) map.get(keyPrefix);
                            /**
                             * 判断选择的是无前缀
                             */
                            if (StringUtils.isNotEmpty(valuePrefix) && !"-1".equals(valuePrefix)){
                                String  variableValue=getEnvVariableRelation(variableRelationList,envCode,valuePrefix);
                                if (StringUtils.isNotEmpty(variableValue)) {
                                    map.put(k, (variableValue + v.toString()));
                                }
                            }
                        }
                    });
                    return map;
                }
            }catch (Exception e){
                log.error("formatVariable error json:{}",json,e);
            }
        }
        return null;
    }
    private String getEnvVariableRelation(List<EnvVariableRelationEntity> envVariableRelationList,String envCode,String variableKey){
        if (CollectionUtils.isNotEmpty(envVariableRelationList)){
            Optional<EnvVariableRelationEntity> first = envVariableRelationList
                    .stream()
                    .filter(m -> m.getEnvCode().equals(envCode) && m.getVariableKey().equals(variableKey))
                    .findFirst();
            if (first.isPresent()){
                return first.get().getVariableValue();
            }
        }
        return "";
    }

    @Override
    public Long clusterSync(DataDynamicApiReq req) {
        String key = getKey(req.getEnvCode(),req.getAppCode());
        long version =req.getVersion().longValue();
        long v = 0;
        if (versionCache.containsKey(key)){
            v = versionCache.get(key).longValue();
        }
        if(v<version){
            versionCache.put(key,req.getVersion());
        }
        return versionCache.get(key);
    }



}