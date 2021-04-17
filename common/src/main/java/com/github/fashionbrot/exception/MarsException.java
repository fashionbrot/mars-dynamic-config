package com.github.fashionbrot.exception;


import com.github.fashionbrot.enums.RespEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarsException extends RuntimeException {

    private int code;
    private String msg;


    public MarsException(String msg){
        super(msg);
        this.code = RespEnum.FAIL.getCode();
        this.msg = msg;
    }

    public MarsException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public MarsException(RespEnum respCode){
        super(respCode.getMsg());
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }

    public MarsException(RespEnum respCode, String msg){
        super(respCode.getMsg());
        this.code = respCode.getCode();
        this.msg = msg+respCode.getMsg();
    }

}
