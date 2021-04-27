package com.github.fashionbrot.controller.open;

import com.github.fashionbrot.req.DataDynamicApiReq;
import com.github.fashionbrot.service.DynamicDataService;
import com.github.fashionbrot.vo.ApiRespVo;
import com.github.fashionbrot.vo.RespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/open/")
public class DataDynamicApi {

    @Autowired
    private DynamicDataService dynamicDataService;



    @RequestMapping("/data/dynamic/version")
    @ResponseBody
    public RespVo checkVersion(DataDynamicApiReq req){
        return RespVo.success(dynamicDataService.checkVersion(req));
    }

    @RequestMapping("/data/dynamic/for-data")
    @ResponseBody
    public ApiRespVo getData(DataDynamicApiReq req){
        return dynamicDataService.getData(req);
    }


    @PostMapping("/data/dynamic/cluster/sync")
    @ResponseBody
    public Long clusterSync(DataDynamicApiReq req){
        return dynamicDataService.clusterSync(req);
    }

}
