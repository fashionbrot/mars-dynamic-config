package com.github.fashionbrot.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* 统一返回vo 类
*/
@Data
@Builder
@AllArgsConstructor
public class RespVo implements Serializable{
    private static final long serialVersionUID = -3655390020082644681L;

    public static final int SUCCESS = 0;
    public static final int FAILED = 1;

    private int code;
    private String msg;
    private List<DataDynamicVo> data;
    private Long version;


    public static RespVo fail(String msg){
        return RespVo.builder().code(FAILED).msg(msg).build();
    }

    public static RespVo fail(String msg, int code){
        return RespVo.builder().code(code).msg(msg).build();
    }



    public boolean isSuccess() {
        return code==SUCCESS;
    }
}
