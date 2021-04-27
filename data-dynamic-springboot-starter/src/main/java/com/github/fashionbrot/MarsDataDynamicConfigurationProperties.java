package com.github.fashionbrot;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mars.data.dynamic")
public class MarsDataDynamicConfigurationProperties {

    /**
     * 环境code
     */
    private String envCode;

    /**
     * 应用code
     */
    private String appCode;

    /**
     * 服务器地址 多个逗号分隔
     */
    private String serverAddress;

    /**
     * 客户端轮训毫秒数
     */
    private Long listenLongPollMs=10000L;

    /**
     * 集群配置
     * @return
     */
    private String cluster;



    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public Long getListenLongPollMs() {
        return listenLongPollMs;
    }

    public void setListenLongPollMs(Long listenLongPollMs) {
        this.listenLongPollMs = listenLongPollMs;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }
}
