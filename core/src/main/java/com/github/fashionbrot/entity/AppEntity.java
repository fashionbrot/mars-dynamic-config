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
 * 应用表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@ApiModel(value = "应用表")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("m_app")
public class AppEntity implements Serializable {


	private static final long serialVersionUID = -4907363025460878323L;
	@ApiModelProperty(value = "自增id")
	@TableId(type = IdType.AUTO)
	private Long id;

	@ApiModelProperty(value = "应用")
	@TableField("app_code")
	private String appCode;

	@ApiModelProperty(value = "应用说明")
	@TableField("app_desc")
	private String appDesc;

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