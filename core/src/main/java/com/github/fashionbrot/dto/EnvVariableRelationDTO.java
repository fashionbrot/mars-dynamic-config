package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 常量和环境关系表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-20
 */
@Data
@ApiModel(value = "常量和环境关系表")
public class EnvVariableRelationDTO implements Serializable {


	private static final long serialVersionUID = 4568068834299304181L;

	@ApiModelProperty(value = "自增id")
	private Long id;

	@ApiModelProperty(value = "环境code")
	private String envCode;

	@ApiModelProperty(value = "常量值")
	private String variableValue;

	@ApiModelProperty(value = "常量key")
	private String variableKey;

}