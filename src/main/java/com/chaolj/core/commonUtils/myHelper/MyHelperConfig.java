package com.chaolj.core.commonUtils.myHelper;

import com.chaolj.core.commonUtils.myHelper.Impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MyHelperProperties.class)
public class MyHelperConfig {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MyHelperProperties myHelperProperties;

    @Bean(name = "myHelperTemplate")
    public MyHelperTemplate HelperTemplate(){
        return new MyHelperTemplate(applicationContext);
    }

    @Bean(name = "myCommonHelper")
    public CommonHelper MyCommonHelper(){
        return new CommonHelper(applicationContext, myHelperProperties);
    }

    @Bean(name = "myConfigHelper")
    public ConfigHelper MyConfigHelper(){
        return new ConfigHelper(applicationContext, myHelperProperties);
    }

    @Bean(name = "myCSVHelper")
    public CSVHelper MyCSVHelper(){
        return new CSVHelper(applicationContext, myHelperProperties);
    }

    @Bean(name = "myEncryptHelper")
    public EncryptHelper MyEncryptHelper(){
        return new EncryptHelper(applicationContext, myHelperProperties);
    }

    @Bean(name = "myGuidHelper")
    public GuidHelper MyGuidHelper(){
        return new GuidHelper(applicationContext, myHelperProperties);
    }

    @Bean(name = "myJsonHelper")
    public JsonHelper MyJsonHelper(){
        return new JsonHelper(applicationContext, myHelperProperties);
    }

    @Bean(name = "myServletHelper")
    public ServletHelper MyServletHelper(){
        return new ServletHelper(applicationContext, myHelperProperties);
    }

    @Bean(name = "myValidateHelper")
    public ValidateHelper MyValidateHelper(){
        return new ValidateHelper(applicationContext, myHelperProperties);
    }
}
