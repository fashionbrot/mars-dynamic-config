package com.github.fashionbrot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReleaseTypeEnum {

    UPDATE(0,"已修改"),
    RELEASE(1,"已发布"),
    DELETE(2,"已删除"),
    ADD(3,"新增"),
    ;

    private int code;
    private String desc;
}
