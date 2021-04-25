package com.github.fashionbrot.req;

import lombok.Data;

@Data
public class DynamicDataLogReq extends PageReq {

    private String envCode;

    private String appCode;

    private String templateKey;

}
