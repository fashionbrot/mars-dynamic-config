package com.github.fashionbrot.req;

import lombok.Data;

@Data
public class PropertyReq extends PageReq {

    private String appCode;

    private String templateKey;

    private Integer showTable;
}
