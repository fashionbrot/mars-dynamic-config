package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 动态配置数据表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@Data
@ApiModel(value = "动态配置数据表")
public class DynamicDataValueDTO implements Serializable {


	private static final long serialVersionUID = 5821322281219564128L;

	@ApiModelProperty(value = "自增id")
	private Long id;

	@ApiModelProperty(value = "动态配置表id")
	private Long dataId;

	@ApiModelProperty(value = "实例json")
	private String json;

	@ApiModelProperty(value = "temp json")
	private String tempJson;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	private Integer delFlag;

}