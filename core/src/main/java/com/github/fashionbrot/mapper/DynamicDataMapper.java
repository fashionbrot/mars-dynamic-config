
package com.github.fashionbrot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.fashionbrot.entity.DynamicDataEntity;
import com.github.fashionbrot.req.DynamicDataReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 动态配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Mapper
public interface DynamicDataMapper extends BaseMapper<DynamicDataEntity> {


    List<DynamicDataEntity> pageReq(DynamicDataReq req);

    List<Map<String,Object>> pageReq2(DynamicDataReq req);
}