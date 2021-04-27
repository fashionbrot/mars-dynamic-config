package com.github.fashionbrot.req;

import lombok.Data;

@Data
public class DataDynamicApiReq {


    private String appCode;

    private String envCode;

    private Long version;

    private String templateKeys;

    private String all;

}
