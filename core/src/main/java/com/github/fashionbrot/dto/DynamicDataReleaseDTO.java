package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 配置数据发布表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Data
@ApiModel(value = "配置数据发布表")
public class DynamicDataReleaseDTO implements Serializable {


	private static final long serialVersionUID = 1755155763134208229L;
	@ApiModelProperty(value = "自增id")
	private Long id;

	@ApiModelProperty(value = "环境code")
	private String envCode;

	@ApiModelProperty(value = "应用名")
	private String appCode;

	@ApiModelProperty(value = "模板keys")
	private String templateKeys;

	@ApiModelProperty(value = "最近更新时间")
	private Date updateDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	private Integer delFlag;

}