package com.github.fashionbrot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemRoleEnum {

    PUSH(0,"发布"),
    DEL(1,"删除"),
    EDIT(2,"修改"),
    VIEW(3,"修改"),
    ;
    private int code;

    private String desc;
}
