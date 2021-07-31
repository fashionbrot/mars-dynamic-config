package com.github.fashionbrot.ribbon.ping;


import com.github.fashionbrot.ribbon.Server;
import com.github.fashionbrot.ribbon.util.HttpClientUtil;
import com.github.fashionbrot.ribbon.util.HttpResult;
import com.github.fashionbrot.ribbon.util.StringUtil;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PingUrl implements IPing {


    @Override
    public boolean isAlive(Server server) {
        String url = server.getServer();
        if (StringUtil.isEmpty(url)){
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
