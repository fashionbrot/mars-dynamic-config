package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.DynamicDataEntity;
import com.github.fashionbrot.entity.DynamicDataLogEntity;
import com.github.fashionbrot.entity.DynamicDataValueEntity;
import com.github.fashionbrot.enums.OperationTypeEnum;
import com.github.fashionbrot.enums.ReleaseTypeEnum;
import com.github.fashionbrot.exception.MarsException;
import com.github.fashionbrot.mapper.DynamicDataLogMapper;
import com.github.fashionbrot.mapper.DynamicDataMapper;
import com.github.fashionbrot.mapper.DynamicDataValueMapper;
import com.github.fashionbrot.req.DynamicDataLogReq;
import com.github.fashionbrot.service.DynamicDataLogService;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 配置数据记录表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Service
public class DynamicDataLogServiceImpl  extends ServiceImpl<DynamicDataLogMapper, DynamicDataLogEntity> implements DynamicDataLogService {

    @Autowired
    private DynamicDataMapper dynamicDataMapper;
    @Autowired
    private DynamicDataValueMapper dynamicDataValueMapper;

    @Override
    public Object pageReq(DynamicDataLogReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<DynamicDataLogEntity> listByMap = baseMapper.selectByReq(req);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rollback(Long id) {
        DynamicDataLogEntity entity = baseMapper.selectById(id);
        if (entity!=null){
            if (entity.getOperationType().intValue() == OperationTypeEnum.EDIT.getCode()){


                DynamicDataValueEntity dynamicDataValueEntity = dynamicDataValueMapper.selectDelById(entity.getDataValueId());
                if (dynamicDataValueEntity==null){
                    throw new MarsException("您要回滚的配置已删除");
                }
                DynamicDataEntity dynamicDataEntity = dynamicDataMapper.selectDelById(dynamicDataValueEntity.getDataId());
                if (dynamicDataEntity==null){
                    throw new MarsException("您要回滚的配置或许已删除");
                }
                if (dynamicDataEntity.getReleaseType().intValue()==ReleaseTypeEnum.RELEASE.getCode()){
                    dynamicDataValueEntity.setTempJson(entity.getJson());
                }else{
                    dynamicDataValueEntity.setTempJson(entity.getTempJson());
                }
                dynamicDataValueEntity.setDelFlag(0);
                dynamicDataValueMapper.updateById(dynamicDataValueEntity);



                dynamicDataEntity.setDelFlag(0);
                dynamicDataEntity.setReleaseType(ReleaseTypeEnum.ROLLBACK.getCode());
                dynamicDataMapper.updateById(dynamicDataEntity);

            }else if (entity.getOperationType().intValue()== OperationTypeEnum.DEL.getCode()){

                DynamicDataValueEntity dynamicDataValueEntity = dynamicDataValueMapper.selectDelById(entity.getDataValueId());
                if (dynamicDataValueEntity==null){
                    throw new MarsException("您要回滚的配置已删除");
                }
                dynamicDataValueMapper.updateDelFlag(dynamicDataValueEntity.getId(),0);


                DynamicDataEntity dynamicDataEntity = dynamicDataMapper.selectDelById(dynamicDataValueEntity.getDataId());
                if (dynamicDataEntity==null){
                    throw new MarsException("您要回滚的配置或许已删除");
                }
                dynamicDataEntity.setReleaseType(ReleaseTypeEnum.ROLLBACK.getCode());
                dynamicDataEntity.setDelFlag(0);
                dynamicDataMapper.updateDelFlagAndReleaseTypeById(dynamicDataEntity);

            }
        }

    }

}