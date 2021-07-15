package com.github.fashionbrot.req;

import lombok.Data;

@Data
public class SystemConfigApiReq {

    private String envCode;

    private String appCode;

    private Long version;

    private String templateKeys;

    private String all;
}
