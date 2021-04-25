
package com.github.fashionbrot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.fashionbrot.entity.DynamicDataLogEntity;
import com.github.fashionbrot.req.DynamicDataLogReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 配置数据记录表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Mapper
public interface DynamicDataLogMapper extends BaseMapper<DynamicDataLogEntity> {


    List<DynamicDataLogEntity> selectByReq(DynamicDataLogReq req);
}