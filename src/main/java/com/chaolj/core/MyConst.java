package com.chaolj.core;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class MyConst {
    public static final String HEADERKEY_TOKEN = "SSOUserToken";
    public static final String HEADERKEY_HttpTrackID = "HttpTrackId";
    public static final String HEADERKEY_GTTrackID = "TX_XID";

    public static final LocalDateTime DATETIME_MIN = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
    public static final LocalDateTime DATETIME_MAX = LocalDateTime.of(9999, 12, 31, 0, 0, 0);

    public static final String Pattern_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String Pattern_yyyyMMddHHmmssSSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String Pattern_yyyyMMdd_T_HHmmss = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String Pattern_yyyyMMdd = "yyyy-MM-dd";
    public static final String Pattern_HHmmss = "HH:mm:ss";
    public static final String Pattern_HHmmssSSS = "HH:mm:ss.SSS";

    public static final List<String> INTERCEPTOR_EXCLUDE = Arrays.asList("/**/*.js", "/**/*.css", "/**/*.ico", "/image/**", "/**/hello", "/doc.html/**", "/swagger-resources/**", "/druid/**");

    public static final String Server_myTokenProvider = "myTokenProvider";
    public static final String Server_myQueueProvider = "myQueueProvider";
    public static final String Server_myGlobalProvider = "myGlobalProvider";
    public static final String Server_myFileProvider = "myFileProvider";
    public static final String Server_myCacheProvider = "myCacheProvider";
    public static final String Server_myElasticsearchProvider = "myElasticsearchProvider";
    public static final String Server_openTokenProvider = "openTokenProvider";
}
