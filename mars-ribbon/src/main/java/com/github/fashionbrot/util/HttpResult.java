package com.github.fashionbrot.util;


public class HttpResult {

    public int code;
    public String content;

    public boolean isSuccess(){
        return 200 == this.code;
    }

    HttpResult(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
