
package com.github.fashionbrot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.fashionbrot.entity.SystemReleaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 系统配置发布表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */
@Mapper
public interface SystemReleaseMapper extends BaseMapper<SystemReleaseEntity> {


    @Select("select id from m_system_release where env_code=#{envCode} and app_code=#{appCode}  and release_flag=#{releaseFlag}  ORDER BY id desc  LIMIT 1")
    Long getTopReleaseId(@Param("envCode") String envCode, @Param("appCode") String appCode, @Param("releaseFlag")Integer releaseFlag);
}