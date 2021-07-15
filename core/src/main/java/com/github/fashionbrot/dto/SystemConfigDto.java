package com.github.fashionbrot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemConfigDto {

    private String envCode;

    private String appCode;

    private Integer updateReleaseStatus;

    private List<Integer> whereReleaseStatus;

}
