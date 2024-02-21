package com.chaolj.core.commonUtils.myProxy;

import org.springframework.beans.factory.FactoryBean;
import java.lang.reflect.Proxy;

public class ApiProxyFactoryBean<T> implements FactoryBean<T> {
    private Class<T> interfaces;

    public ApiProxyFactoryBean(Class<T> interfaces) {
        this.interfaces = interfaces;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(interfaces.getClassLoader(), new Class[]{interfaces},
                new ApiProxy<>(interfaces));
    }

    @Override
    public Class<?> getObjectType() {
        return interfaces;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
