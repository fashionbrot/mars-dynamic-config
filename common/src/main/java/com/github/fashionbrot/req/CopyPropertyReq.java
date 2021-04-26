package com.github.fashionbrot.req;

import com.github.fashionbrot.validated.annotation.NotEmpty;
import lombok.Data;

@Data
public class CopyPropertyReq {

    @NotEmpty(msg = "请选择要复制的属性")
    private String sourceIds;

    @NotEmpty(msg = "请选择要到的应用")
    private String targetAppCode;

    @NotEmpty(msg = "请选择要到的应用模板")
    private String targetTemplateKey;

}
