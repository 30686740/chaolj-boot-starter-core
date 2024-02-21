package com.chaolj.core.commonUtils.myHelper;

import org.springframework.context.ApplicationContext;
import com.chaolj.core.commonUtils.myHelper.Impl.*;

public class MyHelperTemplate {
    private ApplicationContext applicationContext;

    public MyHelperTemplate(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public CommonHelper CommonHelper() { return this.applicationContext.getBean(CommonHelper.class); }
    public ConfigHelper ConfigHelper() { return this.applicationContext.getBean(ConfigHelper.class); }
    public CSVHelper CSVHelper() { return this.applicationContext.getBean(CSVHelper.class); }
    public EncryptHelper EncryptHelper() { return this.applicationContext.getBean(EncryptHelper.class); }
    public GuidHelper GuidHelper() { return this.applicationContext.getBean(GuidHelper.class); }
    public JsonHelper JsonHelper() { return this.applicationContext.getBean(JsonHelper.class); }
    public ServletHelper ServletHelper() { return this.applicationContext.getBean(ServletHelper.class); }
    public ValidateHelper ValidateHelper() { return this.applicationContext.getBean(ValidateHelper.class); }
}
