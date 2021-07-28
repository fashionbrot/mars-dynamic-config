package com.github.fashionbrot.controller.open;


import com.github.fashionbrot.req.DataConfigReq;
import com.github.fashionbrot.req.SystemConfigApiReq;
import com.github.fashionbrot.service.SystemConfigService;
import com.github.fashionbrot.vo.ForDataVoList;
import com.github.fashionbrot.vo.RespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author fashionbrot
 * @version 0.1.0
 */

@RequestMapping("/open")
@Controller
public class SystemDynamicConfigApi {

    @Autowired
    private SystemConfigService systemConfigService;


    @PostMapping("/config/check-for-update")
    @ResponseBody
    public long checkForUpdate(DataConfigReq dataConfig){
        return systemConfigService.checkForUpdate(dataConfig);
    }

    @PostMapping("/config/for-data")
    @ResponseBody
    public ForDataVoList forDataVo(DataConfigReq dataConfig){
        return systemConfigService.forDataVo(dataConfig);
    }

    @PostMapping("/api/config/cluster/sync")
    @ResponseBody
    public Long clusterSync(SystemConfigApiReq apiReq){
        return systemConfigService.clusterSync(apiReq);
    }

}
