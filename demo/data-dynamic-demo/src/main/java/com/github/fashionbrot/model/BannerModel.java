package com.github.fashionbrot.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BannerModel implements Serializable {

    private static final long serialVersionUID = -191028016363805360L;

    private String title;

    private String jumpUrl;

}
