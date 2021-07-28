package com.github.fashionbrot.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalMarsProperties {

    public static final String BEAN_NAME = "globalMarsProperties";

    /**
     * application code
     */
    private String appCode;
    /**
     * env code
     */
    private String envCode;
    /**
     * server ip:port  separator[,]
     */
    private String serverAddress;
    /**
     * listen long poll timeout
     */
    private long listenLongPollMs;

    private boolean enableLocalCache;

    private String localCachePath;
}
