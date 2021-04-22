package com.github.fashionbrot.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 动态配置数据表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-22
 */
@ApiModel(value = "动态配置数据表")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("m_dynamic_data_value")
public class DynamicDataValueEntity implements Serializable {


	private static final long serialVersionUID = -4988133868760460022L;
	@ApiModelProperty(value = "自增id")
	@TableId(type = IdType.AUTO)
	private Long id;

	@ApiModelProperty(value = "动态配置表id")
	@TableField("data_id")
	private Long dataId;

	@ApiModelProperty(value = "实例json")
	@TableField("json")
	private String json;

	@ApiModelProperty(value = "temp json")
	@TableField("temp_json")
	private String tempJson;

	@ApiModelProperty(value = "删除标志位 1删除 0未删除")
	@TableLogic(value = "0", delval = "1")
	@TableField(value = "del_flag",fill = FieldFill.INSERT)
	private Integer delFlag;


}