package com.github.fashionbrot.req;

import lombok.Data;

@Data
public class SystemConfigHistoryReq extends PageReq {

    private String envCode;
    private String appCode;

}
