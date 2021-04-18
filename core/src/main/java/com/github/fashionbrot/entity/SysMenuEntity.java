package com.github.fashionbrot.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@ApiModel(value = "菜单表")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_menu")
public class SysMenuEntity implements Serializable {


	private static final long serialVersionUID = -7373020070668974417L;

	@ApiModelProperty(value = "自增id")
	@TableId(type = IdType.AUTO)
	private Long id;

	@ApiModelProperty(value = "菜单名称")
	@TableField("menu_name")
	private String menuName;

	@ApiModelProperty(value = "菜单级别")
	@TableField("menu_level")
	private Integer menuLevel;

	@ApiModelProperty(value = "菜单url")
	@TableField("menu_url")
	private String menuUrl;

	@ApiModelProperty(value = "父菜单id")
	@TableField("parent_menu_id")
	private Long parentMenuId;

	@ApiModelProperty(value = "显示优先级")
	@TableField("priority")
	private Integer priority;

	@ApiModelProperty(value = "权限code")
	@TableField("permission")
	private String permission;

	@ApiModelProperty(value = "打开方式（menuItem页签 menuBlank新窗口）")
	@TableField("target")
	private String target;

	@ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
	@TableField("visible")
	private Integer visible;

	@ApiModelProperty(value = "是否刷新（0刷新 1不刷新）")
	@TableField("is_refresh")
	private Integer isRefresh;

	@ApiModelProperty(value = "菜单图标")
	@TableField("icon")
	private String icon;

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



	/**
	 * 父级 菜单名称
	 */
	@TableField(exist = false)
	private  String parentMenuName;

	@TableField(exist = false)
	private List<SysMenuEntity> childMenu;

	@TableField(exist = false)
	private  int active;

	@TableField(exist = false)
	private  boolean checked;

	@TableField(exist = false)
	private  boolean open;

	@TableField(exist = false)
	private  String name;

}