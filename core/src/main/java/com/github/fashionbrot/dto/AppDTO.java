package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 应用表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Data
@ApiModel(value = "应用表")
public class AppDTO implements Serializable {


	private static final long serialVersionUID = 7582766898672873940L;
	@ApiModelProperty(value = "自增id")
	private Long id;

	@ApiModelProperty(value = "应用")
	private String appCode;

	@ApiModelProperty(value = "应用说明")
	private String appDesc;

	@ApiModelProperty(value = "创建者id")
	private Long createId;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	private Integer delFlag;

}