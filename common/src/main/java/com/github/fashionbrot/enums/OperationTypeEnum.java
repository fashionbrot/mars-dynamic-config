package com.github.fashionbrot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  OperationTypeEnum {

    EDIT(1,"编辑"),
    DEL(2,"删除");

    private int code;

    private String desc;
}
