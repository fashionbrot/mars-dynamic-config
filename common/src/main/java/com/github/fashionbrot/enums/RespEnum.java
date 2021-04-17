package com.github.fashionbrot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RespEnum {

    SUCCESS(0, "成功"),
    FAIL(1,"网络繁忙，请稍后重试"),

    NEED_LOGIN(10,"请重新登录"),
    SIGNATURE_MISMATCH(11, "签名验证失败"),
    NOT_PERMISSION_ERROR(12,"无权限操作，请开通权限后再来操作"),
    USER_OR_PASSWORD_IS_ERROR(13,"用户名或者密码错误,请重新输入"),

    SAVE_ERROR(100,"保存失败,请稍后再试"),
    UPDATE_ERROR(101,"更新失败,请稍后再试"),
    DELETE_ERROR(102,"删除失败,请稍后再试"),

    ;

    private int code;
    private String msg;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RespCode:[").append(code).append(":").append(msg).append("]");
        return sb.toString();
    }
}
