package com.github.fashionbrot.req;

import com.github.fashionbrot.annotation.ToMapIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分页req")
public class PageReq {

    @ToMapIgnore
    @ApiModelProperty(value = "当前页码")
    private int pageNum=1;

    @ToMapIgnore
    @ApiModelProperty(value = "每页条数")
    private int pageSize=10;
}
