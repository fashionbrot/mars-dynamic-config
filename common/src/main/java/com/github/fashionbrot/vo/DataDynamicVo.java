package com.github.fashionbrot.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataDynamicVo {

    private List<DataDynamicJsonVo> jsonList;

    private List<JSONObject> json;

    private String templateKey;

}
