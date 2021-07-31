package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.dto.SysLogDTO;
import com.github.fashionbrot.entity.SysLogEntity;
import com.github.fashionbrot.mapper.SysLogMapper;
import com.github.fashionbrot.req.SysLogReq;
import com.github.fashionbrot.service.SysLogService;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统日志
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements SysLogService {


    @Override
    public Object pageReq(SysLogReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<SysLogDTO> list = baseMapper.selectListByReq(req);

        return PageVo.builder()
                .rows(list)
                .total(page.getTotal())
                .build();

    }

}