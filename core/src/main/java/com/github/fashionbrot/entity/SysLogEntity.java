package com.github.fashionbrot.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
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
@ApiModel(value = "系统日志")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_log")
public class SysLogEntity implements Serializable {


	private static final long serialVersionUID = 7727293575349000035L;

	@ApiModelProperty(value = "自增id")
	@TableId(type = IdType.AUTO)
	private Long id;

	@ApiModelProperty(value = "请求描述")
	@TableField("request_desc")
	private String requestDesc;

	@ApiModelProperty(value = "请求url")
	@TableField("request_url")
	private String requestUrl;

	@ApiModelProperty(value = "请求方法 1post 2get ")
	@TableField("method_type")
	private Integer methodType;

	@ApiModelProperty(value = "请求ip")
	@TableField("request_ip")
	private String requestIp;

	@ApiModelProperty(value = "请求参数")
	@TableField("request_param")
	private String requestParam;

	@ApiModelProperty(value = "接口方法")
	@TableField("target_method")
	private String targetMethod;

	@ApiModelProperty(value = "1 请求日志 2:异常日志")
	@TableField("log_type")
	private Integer logType;

	@ApiModelProperty(value = "接口类型 1:后台日志 ")
	@TableField("interface_type")
	private Integer interfaceType;

	@ApiModelProperty(value = "创建者id")
	private Long createId;

	@ApiModelProperty(value = "创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(value="create_date",fill = FieldFill.INSERT)
	private Date createDate;

	@ApiModelProperty(value = "异常日志")
	@TableField("exception")
	private String exception;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	@TableLogic(value = "0", delval = "1")
	@TableField(value = "del_flag",fill = FieldFill.INSERT)
	private Integer delFlag;

	@TableField(exist = false)
	private String createName;

}
