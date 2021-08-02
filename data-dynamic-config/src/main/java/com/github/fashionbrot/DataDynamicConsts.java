package com.github.fashionbrot;

/**
 * @author fashionbrot
 * @version 0.1.0
 * @date 2020/11/23 22:45
 */
public class DataDynamicConsts {

    public static final String HEALTH = "/open/health";

    public static final String VERSION = "1.0.0";

    public static final String HTTP = "http://";

    public static final String HTTPS = "https://";

    public static final String CHECK_FOR_UPDATE_PATH ="/open/data/dynamic/version";

    public static final String HTTP_CHECK_VERSION =HTTP+"%s"+CHECK_FOR_UPDATE_PATH;

    public static final String HTTPS_CHECK_VERSION =HTTPS+"%s"+CHECK_FOR_UPDATE_PATH;

    public static final String FOR_DATA ="/open/data/dynamic/for-data";

    public static final String HTTP_LOAD_DATA=HTTP+"%s"+FOR_DATA;

    public static final String HTTPS_LOAD_DATA=HTTPS+"%s"+FOR_DATA;

    public static final String NAME = "mars_";



    public static final String APP_CODE = "${mars.dynamic.data.app-code}";

    public static final String ENV_CODE = "${mars.dynamic.data.env-code}";

    public static final String SERVER_ADDRESS = "${mars.dynamic.data.server-address}";

    public static final String LOCAL_CACHE_PATH = "${mars.dynamic.data.local-cache-path}";

    public static final String LISTEN_LONG_POLL_MS = "${mars.dynamic.data.listen-long-poll-ms}";

    public static final String CLUSTER = "${mars.dynamic.data.cluster}";
}
