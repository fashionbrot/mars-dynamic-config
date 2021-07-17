package com.github.fashionbrot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.fashionbrot.entity.SequenceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface SequenceMapper extends BaseMapper<SequenceEntity> {


    @Select("select m_next_val(#{s_name})")
    Long nextValue(@Param("s_name") String name);

}
