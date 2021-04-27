package com.github.fashionbrot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataDynamicJsonDTO {

    private String json;

    private String templateKey;
}
