
package com.github.fashionbrot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.fashionbrot.dto.SysLogDTO;
import com.github.fashionbrot.entity.SysLogEntity;
import com.github.fashionbrot.req.SysLogReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统日志
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLogEntity> {


    List<SysLogDTO> selectListByReq(SysLogReq req);
}