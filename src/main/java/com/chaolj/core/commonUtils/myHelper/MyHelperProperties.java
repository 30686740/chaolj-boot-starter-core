package com.chaolj.core.commonUtils.myHelper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "myhelper")
public class MyHelperProperties {
    private Integer HttpTimeout = 300000;
}
