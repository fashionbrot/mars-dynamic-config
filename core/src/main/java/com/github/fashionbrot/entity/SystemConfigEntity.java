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
 * 应用系统配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-12
 */
@ApiModel(value = "应用系统配置表")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("m_system_config")
public class SystemConfigEntity implements Serializable {



	@ApiModelProperty(value = "自增id")
	@TableId(type = IdType.AUTO)
	private Long id;

	@ApiModelProperty(value = "应用名称")
	@TableField("app_code")
	private String appCode;

	@ApiModelProperty(value = "环境code")
	@TableField("env_code")
	private String envCode;

	@ApiModelProperty(value = "修改人")
	@TableField("modifier")
	private String modifier;

	@ApiModelProperty(value = "文件名称")
	@TableField("file_name")
	private String fileName;

	@ApiModelProperty(value = "文件描述")
	@TableField("file_desc")
	private String fileDesc;

	@ApiModelProperty(value = "文件类型 TEXT YAML  PROPERTIES")
	@TableField("file_type")
	private String fileType;

	@ApiModelProperty(value = "配置文件内容")
	@TableField("json")
	private String json;

	@ApiModelProperty(value = "临时数据")
	@TableField("temp_json")
	private String tempJson;

	@ApiModelProperty(value = "状态 1新增 2更新 3删除 4已发布")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(value="create_date",fill = FieldFill.INSERT)
	private Date createDate;

	@ApiModelProperty(value = "最近更新时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(value="update_date",fill = FieldFill.UPDATE)
	private Date updateDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	@TableLogic(value = "0", delval = "1")
	@TableField(value = "del_flag",fill = FieldFill.INSERT)
	private Integer delFlag;

	@ApiModelProperty(value = "主题")
	private String theme;

}