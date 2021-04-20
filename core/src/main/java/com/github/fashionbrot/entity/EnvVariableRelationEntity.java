package com.github.fashionbrot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 常量和环境关系表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-20
 */
@ApiModel(value = "常量和环境关系表")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("m_env_variable_relation")
public class EnvVariableRelationEntity implements Serializable {

	private static final long serialVersionUID = -1019209108188978084L;

	@ApiModelProperty(value = "自增id")
	@TableId(type = IdType.AUTO)
	private Long id;

	@ApiModelProperty(value = "环境code")
	@TableField("env_code")
	private String envCode;

	@ApiModelProperty(value = "常量值")
	@TableField("variable_value")
	private String variableValue;

	@ApiModelProperty(value = "常量key")
	@TableField("variable_key")
	private String variableKey;


}