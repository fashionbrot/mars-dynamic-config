package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.dto.SystemConfigDto;
import com.github.fashionbrot.entity.SystemConfigEntity;
import com.github.fashionbrot.entity.SystemConfigHistoryEntity;
import com.github.fashionbrot.entity.SystemConfigRoleRelationEntity;
import com.github.fashionbrot.entity.SystemReleaseEntity;
import com.github.fashionbrot.enums.RespEnum;
import com.github.fashionbrot.enums.SystemRoleEnum;
import com.github.fashionbrot.exception.MarsException;
import com.github.fashionbrot.mapper.SystemConfigHistoryMapper;
import com.github.fashionbrot.mapper.SystemConfigMapper;
import com.github.fashionbrot.mapper.SystemConfigRoleRelationMapper;
import com.github.fashionbrot.mapper.SystemReleaseMapper;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.req.DataConfigReq;
import com.github.fashionbrot.req.SystemConfigApiReq;
import com.github.fashionbrot.req.SystemConfigReq;
import com.github.fashionbrot.service.SystemConfigCacheService;
import com.github.fashionbrot.service.SystemConfigService;
import com.github.fashionbrot.service.UserLoginService;
import com.github.fashionbrot.tool.CollectionUtil;
import com.github.fashionbrot.tool.HttpClientUtil;
import com.github.fashionbrot.tool.HttpResult;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.util.StringUtil;
import com.github.fashionbrot.vo.ForDataVo;
import com.github.fashionbrot.vo.ForDataVoList;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 应用系统配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-12
 */
@SuppressWarnings("ALL")
@Service
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfigEntity> implements SystemConfigService {


    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private SystemConfigHistoryMapper systemConfigHistoryMapper;
    @Autowired
    private SystemConfigRoleRelationMapper systemConfigRoleRelationMapper;
    @Autowired
    private SystemReleaseMapper systemReleaseMapper;
    @Autowired
    private SystemConfigCacheService systemConfigCacheService;

    @Autowired
    private Environment environment;

    private static final String CLUSTER = "mars.cluster.address";
    private static final String SYNC_RETRY = "mars.cluster.sync.retry";
    private static final String SYSTEM_CONFIG_DEL = "[del]";


    private ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(5),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    if (!executor.isShutdown()) {
                        //再尝试入队
                        executor.execute(r);
                    }
                }
            });

    /**
     * 判断是否有权限
     *
     * @param systemConfigId
     * @param roleEnum
     */
    public void checkRole(Long systemConfigId, SystemRoleEnum roleEnum) {
        LoginModel login = userLoginService.getLogin();
        if (!login.isSuperAdmin()) {
            QueryWrapper q = new QueryWrapper();
            q.eq("role_id", login.getRoleId());
            q.eq("system_config_id", systemConfigId);
            SystemConfigRoleRelationEntity systemConfigRoleRelationEntity = systemConfigRoleRelationMapper.selectOne(q);
            if (systemConfigRoleRelationEntity == null) {
                throw new MarsException(RespEnum.NOT_PERMISSION_ERROR);
            }
            if ("0".equals(systemConfigRoleRelationEntity.getPermission().toString().charAt(roleEnum.getCode()))) {
                throw new MarsException(RespEnum.NOT_PERMISSION_ERROR);
            }
        }
    }

    /**
     * 更新发布表
     */
    public void updateRelease(String envCode, String appCode, String fileName) {
        QueryWrapper releaseQuery = new QueryWrapper();
        releaseQuery.eq("env_code", envCode);
        releaseQuery.eq("app_code", appCode);
        SystemReleaseEntity systemReleaseEntity = systemReleaseMapper.selectOne(releaseQuery);
        if (systemReleaseEntity == null) {
            SystemReleaseEntity releaseEntity = SystemReleaseEntity.builder()
                    .envCode(envCode)
                    .appCode(appCode)
                    .releaseFlag(0)
                    .files(fileName)
                    .build();
            systemReleaseMapper.insert(releaseEntity);
        } else {
            String files = fileName;
            if (StringUtil.isNotEmpty(systemReleaseEntity.getFiles())) {
                files = systemReleaseEntity.getFiles() + "," + files;
                List<String> keys = Arrays.stream(files.split(",")).distinct().collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(keys)) {
                    files = String.join(",", keys);
                }
            }
            systemReleaseEntity.setFiles(files);
            systemReleaseMapper.updateById(systemReleaseEntity);
        }
    }

    @Override
    public Object pageReq(SystemConfigReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(), req.getPageSize());
        Map<String, Object> map = ConvertUtil.toMap(req);
        List<SystemConfigEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SystemConfigEntity entity) {
        QueryWrapper q = new QueryWrapper();
        q.eq("env_code", entity.getEnvCode());
        q.eq("app_code", entity.getAppCode());
        q.eq("file_name", entity.getFileName());
        if (baseMapper.selectCount(q) > 0) {
            throw new MarsException(entity.getFileName() + "已存在，请重新填写");
        }

        LoginModel login = userLoginService.getLogin();
        entity.setStatus(1);
        entity.setModifier(login.getUserName());
        baseMapper.insert(entity);

        updateRelease(entity.getEnvCode(),entity.getAppCode(),entity.getFileName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(SystemConfigEntity entity) {
        SystemConfigEntity old = baseMapper.selectById(entity.getId());

        checkRole(entity.getId(), SystemRoleEnum.EDIT);

        QueryWrapper q = new QueryWrapper();
        q.eq("env_code", entity.getEnvCode());
        q.eq("app_code", entity.getAppCode());
        q.eq("file_name", entity.getFileName());
        SystemConfigEntity systemConfigEntity = baseMapper.selectOne(q);
        if (systemConfigEntity != null && systemConfigEntity.getId().longValue() != systemConfigEntity.getId().longValue()) {
            throw new MarsException(entity.getFileName() + "已存在，请重新填写");
        }
        LoginModel login = userLoginService.getLogin();
        entity.setStatus(2);
        entity.setModifier(login.getUserName());
        baseMapper.updateById(entity);

        if (StringUtil.isNotEmpty(old.getJson()) && !old.getJson().equals(entity.getTempJson())) {

            SystemConfigHistoryEntity historyEntity = SystemConfigHistoryEntity.builder().build();
            BeanUtils.copyProperties(old, historyEntity);
            historyEntity.setTempJson(entity.getTempJson());
            systemConfigHistoryMapper.insert(historyEntity);

            updateRelease(entity.getEnvCode(), entity.getAppCode(), entity.getFileName());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        SystemConfigEntity entity = baseMapper.selectById(id);
        if (entity.getStatus() == 1) {
            baseMapper.deleteById(id);
            return;
        }

        checkRole(entity.getId(), SystemRoleEnum.DEL);

        updateRelease(entity.getEnvCode(), entity.getAppCode(), entity.getFileName() + SYSTEM_CONFIG_DEL);

        LoginModel login = userLoginService.getLogin();
        entity.setStatus(3);
        entity.setModifier(login.getUserName());
        baseMapper.updateById(entity);

        SystemConfigHistoryEntity historyEntity = SystemConfigHistoryEntity.builder().build();
        BeanUtils.copyProperties(entity, historyEntity);
        systemConfigHistoryMapper.insert(historyEntity);
    }

    @Override
    public Object selectById(Long id) {

        checkRole(id, SystemRoleEnum.VIEW);

        SystemConfigEntity entity = baseMapper.selectById(id);
        if (entity != null) {
            if (entity.getStatus() == 1 || entity.getStatus() == 2) {
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


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void releaseConfig(SystemConfigEntity req) {

        QueryWrapper q = new QueryWrapper<SystemReleaseEntity>()
                .eq("env_code", req.getEnvCode())
                .eq("app_code", req.getAppCode())
                .eq("release_flag", 0);
        SystemReleaseEntity releaseEntity = systemReleaseMapper.selectOne(q);
        if (releaseEntity == null) {
            throw new MarsException("没有要发布的配置");
        }

        //更新 SystemConfigEntity表 新增 编辑 状态为发布
        baseMapper.updateRelease(SystemConfigDto.builder()
                .envCode(req.getEnvCode())
                .appCode(req.getAppCode())
                .updateReleaseStatus(4)
                .whereReleaseStatus(Arrays.asList(1, 2))
                .build());
        //更新 SystemConfigEntity表 删除 状态为 删除
        deleteByReleaseStatus(req.getEnvCode(), req.getAppCode(), 3);


        //更新 release
        SystemReleaseEntity updateRelease = SystemReleaseEntity.builder()
                .releaseFlag(1)
                .build();
        if (systemReleaseMapper.update(updateRelease, q) != 1) {
            throw new MarsException(RespEnum.FAIL);
        }

        String key = getKey(req.getEnvCode(), req.getAppCode());
        systemConfigCacheService.setCache(key, releaseEntity.getId());

        if (environment.containsProperty(CLUSTER)) {
            String cluster = environment.getProperty(CLUSTER);
            if (StringUtils.isEmpty(cluster)) {
                return;
            }
            final int retry;
            if (environment.containsProperty(SYNC_RETRY)) {
                retry = StringUtil.parseInteger(environment.getProperty(SYNC_RETRY), 3);
            } else {
                retry = 3;
            }

            List<String> serverList = getServerList(cluster, "/api/config/cluster/sync");
            int count = serverList.size();
            if (count <= 0) {
                return;
            }


            List<String> params = new ArrayList<>();
            params.add("envCode");
            params.add(req.getEnvCode());
            params.add("appCode");
            params.add(req.getAppCode());
            params.add("version");
            params.add(releaseEntity.getId() + "");

            for (String s : serverList) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < retry; i++) {
                            HttpResult httpResult = HttpClientUtil.httpPost(s, null, params, "UTF-8", 2000, 2000);
                            if (httpResult.isSuccess() && (releaseEntity.getId().longValue() + "").equals(httpResult.getResponseBody())) {
                                break;
                            }
                        }
                    }
                });
            }
        }
    }

    private void deleteByReleaseStatus(String envCode, String appName, Integer whereReleaseStatus) {
        QueryWrapper qq = new QueryWrapper();
        qq.eq("env_code", envCode);
        qq.eq("app_code", appName);
        qq.eq("status", whereReleaseStatus);
        baseMapper.delete(qq);
    }

    private String getKey(String env, String app) {
        return env + app;
    }

    public static List<String> getServerList(String serverAddress, String url) {

        String[] server = serverAddress.split(",");
        List<String> serverList = new ArrayList<>(server.length);
        if (StringUtil.isNotEmpty(serverAddress)) {
            for (String s : server) {
                String[] svr = s.split(":");
                int port = 80;
                if (svr.length == 2) {
                    port = StringUtil.parseInteger(svr[1], 80);
                }
                String ip = svr[0];
                if (!ip.startsWith("http")) {
                    ip = "http://" + ip;
                }
                ip = ip + ":" + port + url;
                serverList.add(ip);
            }
            return serverList;
        }
        return serverList;
    }


    @Override
    public long checkForUpdate(DataConfigReq req) {
        String key = systemConfigCacheService.getKey(req.getEnvCode(), req.getAppCode());
        if (systemConfigCacheService.containsKey(key)) {
            return systemConfigCacheService.getCache(key);
        }

        Long version = systemReleaseMapper.getTopReleaseId(req.getEnvCode(), req.getAppCode(), 1);
        if (version == null) {
            version = 0L;
        } else {
            systemConfigCacheService.setCache(key, version);
        }

        return version;
    }

    @Override
    public ForDataVoList forDataVo(DataConfigReq req) {
        String key = systemConfigCacheService.getKey(req.getEnvCode(), req.getAppCode());
        QueryWrapper q = new QueryWrapper();
        q.select("file_name,file_type,json");
        q.eq("env_code", req.getEnvCode());
        q.eq("app_code", req.getAppCode());
        SystemReleaseEntity release = null;
        List<String> delKeyList = null;
        List<String> keyList = null;
        //如果是客户端第一次调用,并且 本地缓存没有最新的version，则进行数据库查询
        if (req.getFirst() != null && req.getFirst()) {
            if (!systemConfigCacheService.containsKey(key)) {
                Long version = systemReleaseMapper.getTopReleaseId(req.getEnvCode(), req.getAppCode(), 1);
                if (version != null) {
                    systemConfigCacheService.setCache(key, version);
                }
            }

            List<SystemConfigEntity> list = baseMapper.selectList(q);
            List<ForDataVo> forDataVoList = null;
            if (CollectionUtil.isNotEmpty(list)) {
                forDataVoList = list.stream()
                        .filter(m -> StringUtil.isNotEmpty(m.getJson()))
                        .map(m -> changeForData(m))
                        .collect(Collectors.toList());
            }
            Long lastVersion = systemConfigCacheService.getCache(key);
            return ForDataVoList.builder()
                    .version(lastVersion)
                    .list(forDataVoList)
                    .build();
        } else {
            release = systemReleaseMapper.selectById(req.getVersion());
            if (release != null) {
                List<String> stringStream = Arrays.stream(release.getFiles().split(",")).collect(Collectors.toList());
                keyList = stringStream.stream().filter(k -> !k.endsWith(SYSTEM_CONFIG_DEL)).collect(Collectors.toList());
                delKeyList = stringStream.stream().filter(k -> k.endsWith(SYSTEM_CONFIG_DEL)).map(k -> k.replace(SYSTEM_CONFIG_DEL, "")).collect(Collectors.toList());
                if (CollectionUtil.isNotEmpty(keyList)) {
                    q.in("file_name", keyList);
                }
            }

            List<SystemConfigEntity> list = null;
            if (CollectionUtil.isNotEmpty(keyList)) {
                list = baseMapper.selectList(q);
            }
            List<ForDataVo> forDataVoList = null;
            if (CollectionUtil.isNotEmpty(list)) {
                forDataVoList = list.stream()
                        .filter(m -> StringUtil.isNotEmpty(m.getJson()))
                        .map(m -> changeForData(m))
                        .collect(Collectors.toList());

                addDelKey(delKeyList, forDataVoList);
            } else {
                forDataVoList = new ArrayList<>(delKeyList.size());
                addDelKey(delKeyList, forDataVoList);
            }

            Long lastVersion = systemConfigCacheService.getCache(key);
            return ForDataVoList.builder()
                    .version(lastVersion)
                    .list(forDataVoList)
                    .build();

        }
    }

    private ForDataVo changeForData(SystemConfigEntity info) {
        return ForDataVo.builder()
                .fileName(info.getFileName())
                .fileType(info.getFileType())
                .content(info.getJson())
                .build();
    }

    private void addDelKey(List<String> delKeyList, List<ForDataVo> forDataVoList) {
        if (CollectionUtil.isNotEmpty(delKeyList)) {
            for (int i = 0; i < delKeyList.size(); i++) {
                forDataVoList.add(ForDataVo.builder()
                        .fileName(delKeyList.get(i))
                        .fileType("properties")
                        .content("")
                        .delFlag(true)
                        .build());
            }
        }
    }

    @Override
    public Long clusterSync(SystemConfigApiReq req) {
        String key = getKey(req.getEnvCode(),req.getAppCode());
        long version =req.getVersion().longValue();
        long v = 0;
        if (systemConfigCacheService.containsKey(key)){
            v = systemConfigCacheService.getCache(key).longValue();
        }
        if(v<version){
            systemConfigCacheService.setCache(key,req.getVersion());
        }
        return systemConfigCacheService.getCache(key);
    }
}