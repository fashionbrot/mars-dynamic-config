package com.github.fashionbrot.ribbon.util;

import com.github.fashionbrot.ribbon.consts.GlobalConsts;
import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


@Slf4j
public class HttpClientUtil {

    public static HttpResult httpPost(String url, String params){
        return httpPost(url,null,params, GlobalConsts.ENCODE_UTF8,2000,2000);
    }

    public static HttpResult httpPost(String url,String headers,String params){
        return httpPost(url,headers,params,GlobalConsts.ENCODE_UTF8,2000,2000);
    }

    public static HttpResult httpPost(String url,String headers,String params,String encoding){
        return httpPost(url,headers,params,encoding,2000,2000);
    }




    public static HttpResult httpGet(String url,String params){
        return httpGet(url,null,params,GlobalConsts.ENCODE_UTF8,2000,2000);
    }

    public static HttpResult httpGet(String url,String headers,String params){
        return httpGet(url,headers,params,GlobalConsts.ENCODE_UTF8,2000,2000);
    }

    public static HttpResult httpGet(String url,String headers,String params,String encoding){
        return httpGet(url,headers,params,encoding,2000,2000);
    }

    /**
     * 发送GET请求
     * @param url               url
     * @param headers           header
     * @param paramValues       value
     * @param encoding          encoding
     * @param readTimeoutMs     readTimeoutMs
     * @param connectTimeout    connectTimeout
     * @return HttpResult
     */
    public static HttpResult httpGet(String url,String headers, String paramValues, String encoding, int readTimeoutMs, int connectTimeout) {

        String encodedContent = encodingParams(paramValues, encoding);
        url += (null == encodedContent) ? "" : ("?" + encodedContent);

        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout( readTimeoutMs);

            setHeaders(conn, headers);
            if (StringUtil.isEmpty(headers)){
                conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + encoding);
            }
            conn.connect();

            int respCode = conn.getResponseCode();
            String resp = null;

            if (HttpURLConnection.HTTP_OK == respCode) {
                inputStream = conn.getInputStream();
            } else {
                inputStream = conn.getErrorStream();
            }

            if (inputStream != null) {
                resp = toString(inputStream, encoding);
            }
            return new HttpResult(respCode, resp);
        } catch (Exception e){
            log.error("httpGet error url:{} msg:{}", url, e.getMessage());
            return new HttpResult(500, "Server Internal Error");
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("inputStream close error");
                }
            }
        }
    }




    /**
     * 发送POST
     * @param url               url
     * @param headers           header
     * @param paramValues       value
     * @param encoding          encoding
     * @param readTimeoutMs     readTimeoutMs
     * @param connectTimeout    connectTimeout
     * @return HttpResult
     */
    static public HttpResult httpPost(String url, String headers,String paramValues,String encoding, int readTimeoutMs,int connectTimeout){
        String encodedContent = encodingParams(paramValues, encoding);

        HttpURLConnection conn = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeoutMs);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            setHeaders(conn, headers);
            if (StringUtil.isEmpty(headers)){
                conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + encoding);
            }

            outputStream = conn.getOutputStream();
            if (outputStream != null) {
                outputStream.write(encodedContent != null ? encodedContent.getBytes(encoding) : "?1=1".getBytes());
                outputStream.flush();
            }
            int respCode = conn.getResponseCode();


            if (HttpURLConnection.HTTP_OK == respCode) {
                inputStream = conn.getInputStream();
            } else {
                inputStream = conn.getErrorStream();
            }

            String resp = null;
            if (inputStream != null) {
                resp = toString(inputStream, encoding);
            }

            return new HttpResult(respCode, resp);
        }catch (Exception e){
            log.error("httpPost error url:{} msg:{}", url, e.getMessage());
            return new HttpResult(500, "Server Internal Error");
        } finally {
            if (null != conn) {
                conn.disconnect();
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("inputStream close error");
                }
            }
            if (outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("outputStream close error");
                }
            }
        }
    }



    private static void setHeaders(HttpURLConnection conn, String headers) {
        if (StringUtil.isNotEmpty(headers)) {
            String[] headerGroups  = headers.split("&");
            if (headerGroups!=null && headerGroups.length>0){
                for(int i=0;i<headerGroups.length;i++){
                    String[] split = headerGroups[i].split("=");
                    if (split!=null && split.length==2){
                        conn.addRequestProperty(split[0], split[1]);
                    }
                }
            }
        }
    }



    private static String encodingParams(String paramValues, String encoding) {
        if (StringUtil.isNotEmpty(paramValues)){
            try {
                return URLEncoder.encode(paramValues, encoding);
            } catch (UnsupportedEncodingException e) {
                log.error("encodingParams error param:{} encoding:{}",paramValues,encoding);
            }
        }
        return "";
    }


    private static  String toString(InputStream input, String encoding) throws Exception {
        return CharStreamUtil.toString(new InputStreamReader(input,encoding==null? GlobalConsts.ENCODE_UTF8:encoding));
    }

}
