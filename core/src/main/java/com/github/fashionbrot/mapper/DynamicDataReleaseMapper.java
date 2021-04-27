
package com.github.fashionbrot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.fashionbrot.entity.DynamicDataReleaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 配置数据发布表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Mapper
public interface DynamicDataReleaseMapper extends BaseMapper<DynamicDataReleaseEntity> {


    @Select("select id from m_dynamic_data_release where env_code=#{envCode} and app_code=#{appCode}  ORDER BY id desc  LIMIT 1 ")
    Long getTopReleaseId(@Param("envCode") String envCode, @Param("appCode") String appCode);

}