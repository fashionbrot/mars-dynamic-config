package com.github.fashionbrot.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForDataVo {


    private String fileName;

    private String fileType;

    private String content;

    private Boolean delFlag;
}
