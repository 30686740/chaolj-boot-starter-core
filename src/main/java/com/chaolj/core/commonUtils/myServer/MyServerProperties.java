package com.chaolj.core.commonUtils.myServer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "myservers")
public class MyServerProperties {
    private String defaultQueueServer = "myQueueProvider";
    private String defaultTokenServer = "myTokenProvider";
    private String defaultCacheServer = "myCacheProvider";
    private String defaultExcelServer = "myExcelProvider";
    private String defaultFileServer = "myFileProvider";
    private String defaultGlobalServer = "myGlobalProvider";
    private String defaultElasticsearchServer = "myElasticsearchProvider";
}
