package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 配置数据记录表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Data
@ApiModel(value = "配置数据记录表")
public class DynamicDataLogDTO implements Serializable {


	private static final long serialVersionUID = 8989307355237923872L;
	@ApiModelProperty(value = "自增id")
	private Long id;

	@ApiModelProperty(value = "环境code")
	private String envCode;

	@ApiModelProperty(value = "应用名")
	private String appCode;

	@ApiModelProperty(value = "模板key")
	private String templateKey;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "发布状态 1已发布 0修改 2已删除 3新增")
	private Integer releaseType;

	@ApiModelProperty(value = "实例json")
	private String json;

	@ApiModelProperty(value = "temp json")
	private String tempJson;

	@ApiModelProperty(value = "创建者id")
	private Long createId;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	private Integer delFlag;

}