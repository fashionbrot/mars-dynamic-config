
package com.github.fashionbrot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.fashionbrot.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 菜单表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {


    List<SysMenuEntity> selectMenuRole(Map<String, Object> map);

    List<SysMenuEntity> selectMenuRoleByUser(Map<String,Object> map);

    String selectMenuPermission(@Param("roleId") Long roleId);
}