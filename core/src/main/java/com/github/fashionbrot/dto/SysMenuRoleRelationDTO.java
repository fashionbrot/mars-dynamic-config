package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 菜单-角色关系表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Data
@ApiModel(value = "菜单-角色关系表")
public class SysMenuRoleRelationDTO implements Serializable {


	private static final long serialVersionUID = -8670687648992782304L;
	@ApiModelProperty(value = "自增id")
	private Long id;

	@ApiModelProperty(value = "用户ID")
	private Long menuId;

	@ApiModelProperty(value = "角色ID")
	private Long roleId;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

}