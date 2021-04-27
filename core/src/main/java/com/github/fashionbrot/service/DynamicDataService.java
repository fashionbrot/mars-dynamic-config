package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.DynamicDataEntity;
import com.github.fashionbrot.req.DataDynamicApiReq;
import com.github.fashionbrot.req.DynamicDataReq;
import com.github.fashionbrot.vo.ApiRespVo;

/**
 * 动态配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
public interface DynamicDataService  extends IService<DynamicDataEntity> {

    Object pageReq(DynamicDataReq req);

    void add(DynamicDataEntity entity);

    void edit(DynamicDataEntity entity);

    void deleteById(Long id);

    Object selectById(Long id);

    Object checkVersion(DataDynamicApiReq req);

    ApiRespVo getData(DataDynamicApiReq req);

    Long clusterSync(DataDynamicApiReq req);

    void setVersionCache(String envCode,String appCode,Long releaseId);
}