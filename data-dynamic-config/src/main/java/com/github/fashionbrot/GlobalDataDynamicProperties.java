package com.github.fashionbrot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalDataDynamicProperties {

    public static final String BEAN_NAME = "mars-globalDataDynamicProperties";
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


    private String localCachePath;

}
