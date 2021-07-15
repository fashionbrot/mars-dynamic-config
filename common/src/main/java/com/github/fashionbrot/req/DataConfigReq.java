package com.github.fashionbrot.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataConfigReq {

    private String envCode;

    private String appCode;

    private Long version;

    private Boolean first;

    private String token;
}
