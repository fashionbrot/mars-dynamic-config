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
 * 属性表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@ApiModel(value = "属性表")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("m_property")
public class PropertyEntity implements Serializable {


	private static final long serialVersionUID = -1410019794955897651L;
	@ApiModelProperty(value = "自增id")
	@TableId(type = IdType.AUTO)
	private Long id;

	@ApiModelProperty(value = "属性名称")
	@TableField("property_name")
	private String propertyName;

	@ApiModelProperty(value = "属性key")
	@TableField("property_key")
	private String propertyKey;

	@ApiModelProperty(value = "属性类型")
	@TableField("property_type")
	private String propertyType;

	@ApiModelProperty(value = "属性长度")
	@TableField("column_length")
	private Integer columnLength;

	@ApiModelProperty(value = "html标签类型")
	@TableField("label_type")
	private String labelType;

	@ApiModelProperty(value = "html 标签值")
	@TableField("label_value")
	private String labelValue;

	@ApiModelProperty(value = "默认值")
	@TableField("default_value")
	private String defaultValue;

	@ApiModelProperty("是否必填 1必填 0非必填")
	@TableField("label_required")
	private Integer labelRequired;

	@ApiModelProperty(value = "应用名称")
	@TableField("app_code")
	private String appCode;

	@ApiModelProperty(value = "常量key")
	@TableField("variable_key")
	private String variableKey;

	@ApiModelProperty(value = "模板key ，公共属性为空，指定模板属性不为空")
	@TableField("template_key")
	private String templateKey;

	@ApiModelProperty(value = "0 公共属性 1 模板属性")
	@TableField("attribute_type")
	private Integer attributeType;

	@ApiModelProperty(value = "显示优先级")
	@TableField("priority")
	private Integer priority;

	@ApiModelProperty(value = "是否在table页展示 1展示 0 不展示")
	@TableField("show_table")
	private Integer showTable;

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


}