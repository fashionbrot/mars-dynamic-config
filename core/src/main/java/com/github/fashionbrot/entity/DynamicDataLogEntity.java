package com.github.fashionbrot.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 配置数据记录表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@ApiModel(value = "配置数据记录表")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("m_dynamic_data_log")
public class DynamicDataLogEntity implements Serializable {


	private static final long serialVersionUID = 2396109619318227733L;

	@ApiModelProperty(value = "自增id")
	@TableId(type = IdType.AUTO)
	private Long id;

	@ApiModelProperty(value = "环境code")
	@TableField("env_code")
	private String envCode;

	@ApiModelProperty(value = "应用名")
	@TableField("app_code")
	private String appCode;

	@ApiModelProperty(value = "模板key")
	@TableField("template_key")
	private String templateKey;

	@ApiModelProperty(value = "描述")
	@TableField("description")
	private String description;

	@ApiModelProperty(value = "操作类型 1修改 2删除 ")
	@TableField("operation_type")
	private Integer operationType;

	@ApiModelProperty("配置id")
	@TableField("data_value_id")
	private Long dataValueId;

	@ApiModelProperty(value = "实例json")
	@TableField("json")
	private String json;

	@ApiModelProperty(value = "temp json")
	@TableField("temp_json")
	private String tempJson;

	@ApiModelProperty(value = "创建者id")
	@TableField(value="create_id",fill = FieldFill.INSERT)
	private Long createId;


	@ApiModelProperty(value = "创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(value="create_date",fill = FieldFill.INSERT)
	private Date createDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	@TableLogic(value = "0", delval = "1")
	@TableField(value = "del_flag",fill = FieldFill.INSERT)
	private Integer delFlag;


	@ApiModelProperty(value = "创建者")
	@TableField(exist = false)
	private String createName;
}