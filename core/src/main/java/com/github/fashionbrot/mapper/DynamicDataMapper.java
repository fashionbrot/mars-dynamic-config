
package com.github.fashionbrot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.fashionbrot.dto.DataDynamicJsonDTO;
import com.github.fashionbrot.entity.DynamicDataEntity;
import com.github.fashionbrot.req.DynamicDataReq;
import com.github.fashionbrot.vo.DataDynamicVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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

    int updateRelease(Map<String, Object> map);

    DynamicDataEntity selectDelById(Long dataId);

    @Update("update m_dynamic_data set del_flag=#{el.delFlag} , release_type=#{el.releaseType} where id=#{el.id}")
    int updateDelFlagAndReleaseTypeById(@Param("el") DynamicDataEntity dynamicDataEntity);

    List<DataDynamicVo> selectJsonByReq(Map<String, Object> map);
}