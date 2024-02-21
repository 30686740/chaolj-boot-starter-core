package com.chaolj.core.bootUtils.bootConfig;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppContextAware implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContextAware.ctx = applicationContext;
    }

    private static ApplicationContext ctx = null;

    public static ApplicationContext getInstance() {
        return ctx;
    }
}
