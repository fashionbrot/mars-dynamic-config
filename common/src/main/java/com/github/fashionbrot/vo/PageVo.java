package com.github.fashionbrot.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PageVo {


    private List<?> rows;

    private long total;

}
