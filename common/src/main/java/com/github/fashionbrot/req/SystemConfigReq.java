package com.github.fashionbrot.req;

import lombok.Data;

@Data
public class SystemConfigReq extends PageReq {

    private String envCode;
    private String appCode;
}
