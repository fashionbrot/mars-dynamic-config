package com.github.fashionbrot.controller.open;

import com.github.fashionbrot.vo.RespVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/open/")
public class HealthApi {

    @RequestMapping("/health")
    @ResponseBody
    public RespVo health(){
        return RespVo.success();
    }

}
