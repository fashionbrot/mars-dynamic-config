package com.github.fashionbrot.config;

import com.github.fashionbrot.MarsTemplateKeyMapping;
import com.github.fashionbrot.model.BannerModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateKeyMapping extends MarsTemplateKeyMapping {

    @Override
    public Map<String, Class> initTemplateKeyClass() {
        Map<String,Class> map=new HashMap<>();
        map.put("banner", BannerModel.class);
        return map;
    }
}
