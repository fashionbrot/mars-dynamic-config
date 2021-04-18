package com.github.fashionbrot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 菜单表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Data
@ApiModel(value = "菜单表")
public class SysMenuDTO implements Serializable {


	private static final long serialVersionUID = -2923426443401093322L;
	@ApiModelProperty(value = "自增id")
	private Long id;

	@ApiModelProperty(value = "菜单名称")
	private String menuName;

	@ApiModelProperty(value = "菜单级别")
	private Integer menuLevel;

	@ApiModelProperty(value = "菜单url")
	private String menuUrl;

	@ApiModelProperty(value = "父菜单id")
	private Long parentMenuId;

	@ApiModelProperty(value = "显示优先级")
	private Integer priority;

	@ApiModelProperty(value = "权限code")
	private String permission;

	@ApiModelProperty(value = "打开方式（menuItem页签 menuBlank新窗口）")
	private String target;

	@ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
	private Integer visible;

	@ApiModelProperty(value = "是否刷新（0刷新 1不刷新）")
	private Integer isRefresh;

	@ApiModelProperty(value = "菜单图标")
	private String icon;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "最近更新时间")
	private Date updateDate;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	private Integer delFlag;

}