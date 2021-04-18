package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 模板表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Data
@ApiModel(value = "模板表")
public class TemplateDTO implements Serializable {


	private static final long serialVersionUID = -7413184339320046119L;
	@ApiModelProperty(value = "自增id")
	private Long id;

	@ApiModelProperty(value = "应用")
	private String appCode;

	@ApiModelProperty(value = "模板key")
	private String templateKey;

	@ApiModelProperty(value = "模板名称")
	private String templateName;

	@ApiModelProperty(value = "模板描述")
	private String templateDesc;

	@ApiModelProperty(value = "创建者id")
	private Long createId;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	private Integer delFlag;

}