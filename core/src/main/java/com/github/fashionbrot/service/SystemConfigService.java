package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SystemConfigEntity;
import com.github.fashionbrot.req.DataConfigReq;
import com.github.fashionbrot.req.SystemConfigApiReq;
import com.github.fashionbrot.req.SystemConfigReq;
import com.github.fashionbrot.vo.ForDataVoList;

/**
 * 应用系统配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-12
 */
public interface SystemConfigService extends IService<SystemConfigEntity> {

    Object pageReq(SystemConfigReq req);

    void add(SystemConfigEntity entity);

    void edit(SystemConfigEntity entity);

    void deleteById(Long id);

    Object selectById(Long id);

    void undel(Long id);

    void releaseConfig(SystemConfigEntity req);

    long checkForUpdate(DataConfigReq dataConfig);

    ForDataVoList forDataVo(DataConfigReq dataConfig);

    Long clusterSync(SystemConfigApiReq apiReq);

   void updateRelease(String envCode, String appCode, String fileName);
}