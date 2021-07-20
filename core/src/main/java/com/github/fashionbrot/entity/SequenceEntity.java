package com.github.fashionbrot.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel(value = "自增表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("m_sequence")
public class SequenceEntity {

    @TableField("s_name")
    private String sName;

    private Long number;


}
