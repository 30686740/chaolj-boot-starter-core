package com.chaolj.core.commonUtils.myHelper.Impl;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.chaolj.core.MyApp;
import org.springframework.context.ApplicationContext;
import com.chaolj.core.commonUtils.myHelper.MyHelperProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class ConfigHelper {
    private ApplicationContext applicationContext;
    private MyHelperProperties properties;

    public ConfigHelper(ApplicationContext applicationContext, MyHelperProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    public String getProperty(String path){
        return this.getProperty(path, "");
    }

    public String getProperty(String path, String defaultValue){
        var p = applicationContext.getEnvironment().getProperty(path);
        return StrUtil.isBlank(p) ? defaultValue : p.trim();
    }

    public Boolean getPropertyBoolean(String path, boolean defaultValue){
        var s = this.getProperty(path);
        return MyApp.Of(s).ToBoolean(defaultValue);
    }

    public LocalDateTime getPropertyLocalDateTime(String path, LocalDateTime defaultValue){
        var s = this.getProperty(path);
        return MyApp.Of(s).ToLocalDateTime(defaultValue);
    }

    public Integer getPropertyInteger(String path, Integer defaultValue){
        var s = this.getProperty(path);
        return MyApp.Of(s).ToInteger(defaultValue);
    }

    public Long getPropertyLong(String path, Long defaultValue){
        var s = this.getProperty(path);
        return MyApp.Of(s).ToLong(defaultValue);
    }

    public Double getPropertyDouble(String path, Double defaultValue){
        var s = this.getProperty(path);
        return MyApp.Of(s).ToDouble(defaultValue);
    }

    public String getApplicationProfile(){
        var p = applicationContext.getEnvironment().getProperty("spring.profiles.active");
        return StrUtil.isBlank(p) ? "" : p.trim();
    }

    public String getApplicationName(){
        var p = applicationContext.getEnvironment().getProperty("spring.application.name");
        return StrUtil.isBlank(p) ? "" : p.trim();
    }

    public String getResourceText(String path){
        var text = "";

        try {
            var inputStream = (new ClassPathResource(path)).getStream();
            text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }
}
