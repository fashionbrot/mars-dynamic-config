package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统用户表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-17
 */
@Data
@ApiModel(value = "系统用户表")
public class SysUserDTO implements Serializable {


	private static final long serialVersionUID = -7510794179261085371L;

	@ApiModelProperty(value = "自增ID")
	private Long id;

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "账号")
	private String account;

	@ApiModelProperty(value = "加密密码")
	private String password;

	@ApiModelProperty(value = "密码加盐参数")
	private String salt;

	@ApiModelProperty(value = "用户状态 0关闭 1开启")
	private Integer status;

	@ApiModelProperty(value = "角色ID")
	private Long roleId;

	@ApiModelProperty(value = "是否是超级管理员 0普通 1超级 ")
	private Integer superAdmin;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "最近更新时间")
	private Date updateDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	private Integer delFlag;

}