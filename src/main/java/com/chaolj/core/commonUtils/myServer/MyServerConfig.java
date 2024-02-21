package com.chaolj.core.commonUtils.myServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MyServerProperties.class)
public class MyServerConfig {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MyServerProperties myServerProperties;

    @Bean(name = "myServerTemplate")
    @ConditionalOnMissingBean(MyServerTemplate.class)
    public MyServerTemplate ServerTemplate(){
        return new MyServerTemplate(this.applicationContext, this.myServerProperties);
    }
}
