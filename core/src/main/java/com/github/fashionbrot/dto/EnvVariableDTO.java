package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 常量表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Data
@ApiModel(value = "常量表")
public class EnvVariableDTO implements Serializable {


	private static final long serialVersionUID = 7070483325295278162L;
	@ApiModelProperty(value = "自增id")
	private Long id;

	@ApiModelProperty(value = "变量名称")
	private String variableName;

	@ApiModelProperty(value = "变量说明")
	private String variableDesc;

	@ApiModelProperty(value = "变量key")
	private String variableKey;

	@ApiModelProperty(value = "创建者id")
	private Long createId;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	private Integer delFlag;

}