package com.github.fashionbrot.server;

import com.github.fashionbrot.ribbon.Server;
import com.github.fashionbrot.api.ApiConstant;
import com.github.fashionbrot.api.ForDataVo;
import com.github.fashionbrot.api.ForDataVoList;
import com.github.fashionbrot.config.GlobalMarsProperties;
import com.github.fashionbrot.ribbon.consts.GlobalConsts;
import com.github.fashionbrot.util.*;
import com.github.fashionbrot.enums.ConfigTypeEnum;
import com.github.fashionbrot.env.MarsPropertySource;
import com.github.fashionbrot.support.SourceParseFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class ServerHttpAgent {

    private static Map<String,Long> lastVersion =new ConcurrentHashMap<>();

    public static void loadLocalConfig(GlobalMarsProperties globalMarsProperties, ConfigurableEnvironment environment){
        String appCode = globalMarsProperties.getAppCode();
        if (StringUtil.isEmpty(globalMarsProperties.getLocalCachePath())){
            globalMarsProperties.setLocalCachePath(FileUtil.getUserHome(appCode));
        }
        String keyWord = ApiConstant.NAME+globalMarsProperties.getAppCode()+"_"+globalMarsProperties.getEnvCode();
        List<File> fileList =  FileUtil.searchFiles(new File(globalMarsProperties.getLocalCachePath()),keyWord);
        if (CollectionUtil.isNotEmpty(fileList)){
            for(File file : fileList){
                String[] fileNames = file.getName().split("_");
                String[] fileType = file.getName().split("\\.");
                if (fileNames.length>2 && fileType.length>1){
                    String context = FileUtil.getFileContent(file);
                    if (StringUtil.isNotEmpty(context)) {
                        buildEnv(environment, globalMarsProperties, fileNames[3], context, fileType[1],file);
                    }
                }
            }
        }else{
            log.warn("search path:{} No file found ",globalMarsProperties.getLocalCachePath() );
        }
    }



    public static void saveRemoteResponse(ConfigurableEnvironment environment,
                                GlobalMarsProperties globalProperties,
                                ForDataVoList dataVo){
        MutablePropertySources mutablePropertySources = environment.getPropertySources();
        if (mutablePropertySources==null){
            log.error("environment get property sources is null");
            return;
        }
        if (dataVo!=null && CollectionUtil.isNotEmpty(dataVo.getList())){
            for(ForDataVo vo :dataVo.getList()){
                buildEnvironmentAndWriteDisk(globalProperties,vo,mutablePropertySources);
            }
            setLastVersion(globalProperties.getEnvCode(),globalProperties.getAppCode(),dataVo.getVersion());
        }
    }

    public static void setLastVersion(String envCode,String appName,Long version){
        String key = getKey(envCode,appName);
        lastVersion.put(key,version);
    }

    public static void setLastVersion(String envCode,String appName,Long version,boolean first){
        String key = getKey(envCode,appName);
        if (first){
            Long clientVersion = lastVersion.get(key);
            if (log.isDebugEnabled()){
                log.debug("last-version clientVersion:{} responseVersion:{}",clientVersion,version);
            }
            if (clientVersion.longValue()+1 < version.longValue()){
                lastVersion.put(key,clientVersion.longValue()+1);
            }else if (clientVersion.longValue()+1 == version.longValue()){
                lastVersion.put(key,version);
            }
        }else{
            lastVersion.put(key,version);
        }
    }

    /**
     * ?????? Environment ????????????????????????
     * @param properties
     * @param vo
     * @param mutablePropertySources
     */
    public static void buildEnvironmentAndWriteDisk(GlobalMarsProperties properties,ForDataVo vo,MutablePropertySources mutablePropertySources){
        String fileName = vo.getFileName();
        String fileType = vo.getFileType();
        String content = vo.getContent();

        ConfigTypeEnum configTypeEnum = ConfigTypeEnum.valueTypeOf(vo.getFileType());
        Properties value = SourceParseFactory.toProperties(content, configTypeEnum);
        if (value==null){
            value = new Properties();
        }

        if (properties.isEnableLocalCache()){
            if (StringUtil.isEmpty(properties.getLocalCachePath())){
                properties.setLocalCachePath(FileUtil.getUserHome(properties.getAppCode())) ;
            }
            //????????????????????????
           ServerHttpAgent.writePathFile(properties.getLocalCachePath(),properties.getAppCode(),properties.getEnvCode(),fileName,fileType,content);
        }

        String environmentFileName =  ApiConstant.NAME+fileName;

        MarsPropertySource marsPropertySource = new MarsPropertySource(environmentFileName, value);
        mutablePropertySources.addLast(marsPropertySource);
    }


    public static void buildEnv(ConfigurableEnvironment environment,
                                GlobalMarsProperties globalProperties,
                                String fileName,
                                String content,
                                String fileType,
                                File file) {
        MutablePropertySources mutablePropertySources = environment.getPropertySources();
        if (mutablePropertySources == null) {
            log.error("environment get property sources is null");
            return;
        }
        String environmentFileName = ApiConstant.NAME + fileName;
        if (globalProperties != null) {
            Properties properties = SourceParseFactory.toProperties(file, ConfigTypeEnum.valueTypeOf(fileType));
            if (properties != null) {
                MarsPropertySource marsPropertySource = new MarsPropertySource(environmentFileName, properties);
                mutablePropertySources.addLast(marsPropertySource);
            }
        }
    }


    public static ForDataVoList getForData(Server server, String envCode, String appCode, boolean first){

        String params =getParams(envCode,appCode,first);
        String url = getForDataRequestUrl(server);
        HttpResult httpResult =  HttpClientUtil.httpPost(url,null,params);
        if (httpResult.isSuccess()){
            return JsonUtil.parseObject(httpResult.getContent(), ForDataVoList.class);
        }
        return null;
    }

    /**
     * ???????????? version?????????false ???????????????????????????version
     * @param server
     * @param env
     * @param appCode
     * @param first
     * @return
     */
    public static boolean checkForUpdate(Server server, String env, String appCode, boolean first) {

        String params = getParams(env, appCode,first);
        String url = getCheckForUpdateRequestUrl(server);

        HttpResult httpResult  = HttpClientUtil.httpPost(url,null,params, GlobalConsts.ENCODE_UTF8,2000,2000);
        if (httpResult.isSuccess() && StringUtil.isNotEmpty(httpResult.getContent()) ){
            long responseVersion = ObjectUtils.parseLong(httpResult.getContent());
            if (responseVersion == -1 || responseVersion == 0){
                return true;
            }
            String key = getKey(env,appCode);
            if (lastVersion.containsKey(key)){
                Long last = lastVersion.get(key);
                if (last.longValue() < responseVersion) {
                    return false;
                }
            }else{
                lastVersion.put(key,responseVersion);
                return false;
            }

        }
        return true;
    }

    public static String getKey(String envCode,String appName){
        return envCode+appName;
    }

    private static String getCheckForUpdateRequestUrl(Server server) {
        return String.format(ApiConstant.HTTP_CHECK_FOR_UPDATE_PATH_PARAM, server.getServer());
    }

    private static String getParams(String env, String appCode,boolean first) {
        StringBuilder sb=new StringBuilder();
        sb.append("envCode").append("=").append(env);
        sb.append("&");
        sb.append("appCode").append("=").append(appCode);
        sb.append("&");
        sb.append("version").append("=");

        String key = getKey(env,appCode);
        if (lastVersion.containsKey(key)) {
            sb.append(lastVersion.get(key)+1);
        }else{
            sb.append("0");
        }

        if (first){
            sb.append("&");
            sb.append("first").append("=").append("true");
        }
        return sb.toString();
    }

    private static String getForDataRequestUrl(Server server) {
        return String.format(ApiConstant.HTTP_LOAD_DATA, server.getServer());
    }


    /**
     * ????????????????????????????????????
     * @param localCachePath
     * @param appName
     * @param envCode
     * @param fileName
     * @param content
     */
    public static void writePathFile(String localCachePath,String appName,String envCode,String fileName,String fileType,String content){
        StringBuilder path = new StringBuilder();
        path.append(localCachePath).append(File.separator).append(ApiConstant.NAME);
        path.append(appName).append("_");
        path.append(envCode).append("_");
        path.append(fileName).append(".");
        path.append(fileType);
        if (log.isDebugEnabled()){
            log.debug("writePathFile path:{} content:{}",path,content);
        }

        removeSearchFiles(localCachePath, appName, envCode, fileName);

        FileUtil.writeFile(new File(path.toString()),content);
    }

    public static void removeSearchFiles(String localCachePath, String appName, String envCode, String fileName) {
        String keyWord = ApiConstant.NAME+appName+"_"+envCode+"_"+fileName;
        //???????????????file
        List<File> files = FileUtil.searchFiles(new File(localCachePath), keyWord);
        if (CollectionUtil.isNotEmpty(files)){
            files.forEach(f->{
                FileUtil.deleteFile(f);
            });
        }
    }


}
