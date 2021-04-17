package com.github.fashionbrot.exception;


import com.github.fashionbrot.enums.RespEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurdException extends RuntimeException {

    private int code;
    private String msg;


    public CurdException(String msg){
        super(msg);
        this.code = RespEnum.FAIL.getCode();
        this.msg = msg;
    }

    public CurdException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CurdException(RespEnum respCode){
        super(respCode.getMsg());
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }

    public CurdException(RespEnum respCode, String msg){
        super(respCode.getMsg());
        this.code = respCode.getCode();
        this.msg = msg+respCode.getMsg();
    }

}
