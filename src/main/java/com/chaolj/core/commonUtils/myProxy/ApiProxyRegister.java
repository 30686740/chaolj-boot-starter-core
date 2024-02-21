package com.chaolj.core.commonUtils.myProxy;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/* 使用示例
import com.chaolj.core.commonUtils.myProxy.ApiProxyRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiProxyConfig {
    @Bean(name = "apiProxyRegister")
    public ApiProxyRegister myProxyRegister() {
        return new ApiProxyRegister("接口所在的包路径");
    }
}
*/
public class ApiProxyRegister implements BeanDefinitionRegistryPostProcessor {
    private String basePackage;

    public ApiProxyRegister(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (StrUtil.isEmpty(basePackage)) return;
        var scanner = new ApiProxyScanner(registry);
        scanner.doScan(basePackage);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
