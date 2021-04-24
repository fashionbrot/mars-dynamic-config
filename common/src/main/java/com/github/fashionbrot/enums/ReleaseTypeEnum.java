package com.github.fashionbrot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReleaseTypeEnum {

    UPDATE(4,"已修改"),
    RELEASE(5,"已发布"),
    DELETE(6,"已删除"),
    ADD(7,"新增"),
    ;

    private int code;
    private String desc;
}
