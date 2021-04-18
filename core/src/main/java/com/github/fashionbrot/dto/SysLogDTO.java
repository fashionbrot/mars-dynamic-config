package com.github.fashionbrot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统日志
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Data
@ApiModel(value = "系统日志")
public class SysLogDTO implements Serializable {


	private static final long serialVersionUID = -1442953976414659287L;
	@ApiModelProperty(value = "自增id")
	private Long id;

	@ApiModelProperty(value = "请求描述")
	private String requestDesc;

	@ApiModelProperty(value = "请求url")
	private String requestUrl;

	@ApiModelProperty(value = "请求方法 1post 2get ")
	private Integer methodType;

	@ApiModelProperty(value = "请求ip")
	private String requestIp;

	@ApiModelProperty(value = "请求参数")
	private String requestParam;

	@ApiModelProperty(value = "接口方法")
	private String targetMethod;

	@ApiModelProperty(value = "1 请求日志 2:异常日志")
	private Integer logType;

	@ApiModelProperty(value = "接口类型 1:后台日志 ")
	private Integer interfaceType;

	@ApiModelProperty(value = "创建者id")
	private Long createId;

	@ApiModelProperty(value = "创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	@ApiModelProperty(value = "异常日志")
	private String exception;

	private String createName;
}