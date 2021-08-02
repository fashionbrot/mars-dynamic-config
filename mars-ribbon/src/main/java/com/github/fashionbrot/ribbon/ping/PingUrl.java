package com.github.fashionbrot.ribbon.ping;


import com.github.fashionbrot.ribbon.Server;
import com.github.fashionbrot.util.HttpClientUtil;
import com.github.fashionbrot.util.HttpResult;
import com.github.fashionbrot.util.StringUtil;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PingUrl implements IPing {


    @Override
    public boolean isAlive(Server server) {
        String url = server.getServer();
        if (StringUtil.isEmpty(server.getPath())){
            return true;
        }
        HttpResult httpResult;
        try {
            httpResult = HttpClientUtil.httpGet(url,null);
        } catch (Exception e) {
            log.error("pingUrl alive error:", e.getMessage());
            return false;
        }
        if (httpResult.isSuccess()){
            return true;
        }
        return false;
    }

}
