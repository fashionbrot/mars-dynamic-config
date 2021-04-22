package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 动态配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Data
@ApiModel(value = "动态配置表")
public class DynamicDataDTO implements Serializable {


	private static final long serialVersionUID = -649957577957397599L;
	@ApiModelProperty(value = "自增id")
	private Long id;

	@ApiModelProperty(value = "环境code")
	private String envCode;

	@ApiModelProperty(value = "应用名")
	private String appCode;

	@ApiModelProperty(value = "模板key")
	private String templateKey;

	@ApiModelProperty(value = "状态 1开启 0关闭")
	private Integer status;

	@ApiModelProperty(value = "描述")
	private String dataDesc;

	@ApiModelProperty(value = "优先级")
	private Integer priority;

	@ApiModelProperty(value = "发布状态 1已发布 0修改 2已删除 3新增")
	private Integer releaseType;

	@ApiModelProperty(value = "创建者id")
	private Long createId;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "最近更新者id")
	private Long updateId;

	@ApiModelProperty(value = "最近更新时间")
	private Date updateDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	private Integer delFlag;

}