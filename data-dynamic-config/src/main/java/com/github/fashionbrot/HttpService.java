package com.github.fashionbrot;

import com.alibaba.fastjson.JSONObject;
import com.github.fashionbrot.ribbon.enums.SchemeEnum;
import com.github.fashionbrot.ribbon.loadbalancer.Server;
import com.github.fashionbrot.ribbon.util.HttpClientUtil;
import com.github.fashionbrot.ribbon.util.HttpResult;
import com.github.fashionbrot.ribbon.util.StringUtil;
import com.github.fashionbrot.ribbon.util.JsonUtil;
import com.github.fashionbrot.ribbon.util.ObjectUtil;
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

            List<String> params =new ArrayList<>(6);
            params.add("envCode");
            params.add(dataConfig.getEnvCode());
            params.add("appCode");
            params.add(dataConfig.getAppCode());

            params.add("version");
            params.add(version.longValue()+"");


            String url ;
            if (server.getScheme() == SchemeEnum.HTTP) {
                url = String.format(DataDynamicConsts.HTTP_CHECK_VERSION, server.getServerIp());
            } else {
                url = String.format(DataDynamicConsts.HTTPS_CHECK_VERSION, server.getServerIp());
            }

            try {
                HttpResult httpResult =  HttpClientUtil.httpPost(url,null,params);

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


            List<String> params =new ArrayList<>(all?8:6);
            params.add("envCode");
            params.add(dataConfig.getEnvCode());
            params.add("appCode");
            params.add(dataConfig.getAppCode());
            params.add("version");
            params.add((version.longValue()+1)+"");//获取当前version+1 ，不能跳过已发布的配置
            if (all){
                params.add("all");
                params.add("1");
            }


            String url ;
            if (server.getScheme() == SchemeEnum.HTTP) {
                url = String.format(DataDynamicConsts.HTTP_LOAD_DATA, server.getServerIp());
            } else {
                url = String.format(DataDynamicConsts.HTTPS_LOAD_DATA, server.getServerIp());
            }
            try {
                HttpResult httpResult =  HttpClientUtil.httpPost(url,null,params);
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
