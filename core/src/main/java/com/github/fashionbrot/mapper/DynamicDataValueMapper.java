
package com.github.fashionbrot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.fashionbrot.entity.DynamicDataValueEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * 动态配置数据表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Mapper
public interface DynamicDataValueMapper extends BaseMapper<DynamicDataValueEntity> {


    int updateRelease(Map<String, Object> map);

    DynamicDataValueEntity selectDelById(Long dataValueId);

    @Update("update m_dynamic_data_value set del_flag=#{delFlag}  where id =#{id}")
    int updateDelFlag(@Param("id") Long id,@Param("delFlag") int delFlag);
}