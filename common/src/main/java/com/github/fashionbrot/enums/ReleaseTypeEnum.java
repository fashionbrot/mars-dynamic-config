package com.github.fashionbrot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReleaseTypeEnum {

    RELEASE(5,"已发布"),
    UPDATE(4,"已修改"),
    DELETE(6,"已删除"),
    ADD(7,"新增"),
    ROLLBACK(8,"回滚"),
    ;

    private int code;
    private String desc;
}
