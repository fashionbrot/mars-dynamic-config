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
 * 应用系统配置-角色关系表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */
@ApiModel(value = "应用系统配置-角色关系表")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("m_system_config_role_relation")
public class SystemConfigRoleRelationEntity implements Serializable {


	private static final long serialVersionUID = 5036610460903408128L;

	@ApiModelProperty(value = "自增id")
	@TableId(type = IdType.AUTO)
	private Long id;

	@ApiModelProperty(value = "角色ID")
	@TableField("role_id")
	private Long roleId;

	@ApiModelProperty(value = "动态配置ID")
	@TableField("system_config_id")
	private Long systemConfigId;

	@ApiModelProperty(value = "权限 1111 代表 增删改查 都有权限")
	@TableField("permission")
	private Integer permission;

	@ApiModelProperty(value = "创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(value="create_date",fill = FieldFill.INSERT)
	private Date createDate;

	@ApiModelProperty(value = "最近更新者id")
	@TableField(value="update_id",fill = FieldFill.UPDATE)
	private Long updateId;

	@ApiModelProperty(value = "最近更新时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(value="update_date",fill = FieldFill.UPDATE)
	private Date updateDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	@TableLogic(value = "0", delval = "1")
	@TableField(value = "del_flag",fill = FieldFill.INSERT)
	private Integer delFlag;


}