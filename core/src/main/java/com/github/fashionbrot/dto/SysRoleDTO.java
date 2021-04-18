package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 角色表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Data
@ApiModel(value = "角色表")
public class SysRoleDTO implements Serializable {


	private static final long serialVersionUID = 2860715972903890259L;
	@ApiModelProperty(value = "自增ID")
	private Long id;

	@ApiModelProperty(value = "角色描述")
	private String roleName;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "最近更新时间")
	private Date updateDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	private Integer delFlag;

}