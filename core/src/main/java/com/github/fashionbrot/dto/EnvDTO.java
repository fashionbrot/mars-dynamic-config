package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 环境表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Data
@ApiModel(value = "环境表")
public class EnvDTO implements Serializable {


	private static final long serialVersionUID = -986972659100192460L;
	@ApiModelProperty(value = "自增id")
	private Long id;

	@ApiModelProperty(value = "环境")
	private String envCode;

	@ApiModelProperty(value = "环境描述")
	private String envDesc;

	@ApiModelProperty(value = "创建者id")
	private Long createId;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	private Integer delFlag;

}