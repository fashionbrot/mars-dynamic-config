package com.github.fashionbrot;

import com.alibaba.fastjson.JSONObject;
import com.github.fashionbrot.ribbon.Server;
import com.github.fashionbrot.util.HttpClientUtil;
import com.github.fashionbrot.util.HttpResult;
import com.github.fashionbrot.util.StringUtil;
import com.github.fashionbrot.util.JsonUtil;
import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.vo.RespVo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class HttpService {

    private static AtomicLong version = new AtomicLong();

    private static AtomicLong checkVersion = new AtomicLong();

    public static boolean checkVersion(Server server , GlobalDataDynamicProperties dataConfig){
        if (server==null){
            log.warn(" for data server is null");
            return true;
        }

        if (dataConfig!=null){


            StringBuilder sb=new StringBuilder();
            sb.append("envCode").append("=").append(dataConfig.getEnvCode());
            sb.append("&");
            sb.append("appCode").append("=").append(dataConfig.getAppCode());
            sb.append("&");
            sb.append("version").append("=").append(version.longValue());


            String url =String.format(DataDynamicConsts.HTTP_CHECK_VERSION, server.getServer());

            try {
                HttpResult httpResult =  HttpClientUtil.httpPost(url,null,sb.toString());

                if (httpResult!=null && httpResult.isSuccess()){
                    if (StringUtil.isNotEmpty(httpResult.getContent())) {
                        JSONObject jsonObject = JSONObject.parseObject(httpResult.getContent());
                        if (jsonObject!=null && jsonObject.containsKey("data")){
                            Long v = jsonObject.getLong("data");
                            if (v!=null){
                                checkVersion.set(v);
                            }
                            if (v!=null && v.longValue()==-1){
                                return true;
                            }
                            if (v!=null && version.get() < v.longValue()){
                                return false;
                            }
                        }
                    }
                }
            }catch (Exception e) {
                log.error("for-data error  message:{}", e.getMessage());
            }
        }

        return true;
    }

    public static void getData(Server server, GlobalDataDynamicProperties dataConfig, boolean all){
        if (server==null){
            log.warn(" for data server is null");
            return ;
        }

        if (dataConfig!=null){




            StringBuilder sb=new StringBuilder();
            sb.append("envCode").append("=").append(dataConfig.getEnvCode());
            sb.append("&");
            sb.append("appCode").append("=").append(dataConfig.getAppCode());
            sb.append("&");
            sb.append("version").append("=").append(version.longValue());
            if (all){
                sb.append("&");
                sb.append("all=1");
            }


            String url = String.format(DataDynamicConsts.HTTP_LOAD_DATA, server.getServer());

            try {
                HttpResult httpResult =  HttpClientUtil.httpPost(url,null,sb.toString());
                if (httpResult!=null && httpResult.isSuccess() && ObjectUtil.isNotEmpty(httpResult.getContent())){
                    RespVo resp = JsonUtil.parseObject(httpResult.getContent(),RespVo.class);
                    if (resp!=null && resp.isSuccess()){
                        DataDynamicCache.setCache(resp.getData());
                        if (resp.getVersion()!=null) {
                            version.set(resp.getVersion());
                        }
                    }
                }
            }catch (Exception e) {
                log.error("for-data error  message:{}", e.getMessage());
            }
        }
    }


}
