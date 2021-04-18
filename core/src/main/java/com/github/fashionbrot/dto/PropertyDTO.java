package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 属性表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Data
@ApiModel(value = "属性表")
public class PropertyDTO implements Serializable {


	private static final long serialVersionUID = 1282166188135210192L;
	@ApiModelProperty(value = "自增id")
	private Long id;

	@ApiModelProperty(value = "属性名称")
	private String propertyName;

	@ApiModelProperty(value = "属性key")
	private String propertyKey;

	@ApiModelProperty(value = "属性类型")
	private String propertyType;

	@ApiModelProperty(value = "属性长度")
	private Integer columnLength;

	@ApiModelProperty(value = "html标签类型")
	private String labelType;

	@ApiModelProperty(value = "html 标签值")
	private String labelValue;

	@ApiModelProperty(value = "应用名称")
	private String appCode;

	@ApiModelProperty(value = "常量key")
	private String variableKey;

	@ApiModelProperty(value = "模板key ，公共属性为空，指定模板属性不为空")
	private String templateKey;

	@ApiModelProperty(value = "0 公共属性 1 模板属性")
	private Integer attributeType;

	@ApiModelProperty(value = "显示优先级")
	private Integer priority;

	@ApiModelProperty(value = "创建者id")
	private Long createId;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	private Integer delFlag;

}