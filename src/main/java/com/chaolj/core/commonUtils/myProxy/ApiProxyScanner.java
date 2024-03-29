package com.chaolj.core.commonUtils.myProxy;

import java.util.Set;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;

public class ApiProxyScanner extends ClassPathBeanDefinitionScanner {

    ApiProxyScanner(BeanDefinitionRegistry registry) {
        //false表示不使用ClassPathBeanDefinitionScanner默认的TypeFilter
        super(registry, false);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        this.addFilter();
        var beanDefinitionHolders = super.doScan(basePackages);
        if (beanDefinitionHolders.isEmpty()) throw new NullPointerException("No interfaces");
        this.createBeanDefinition(beanDefinitionHolders);
        return beanDefinitionHolders;
    }

    /**
     * 只扫描顶级接口
     * @param beanDefinition bean定义
     * @return boolean
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        String[] interfaceNames = metadata.getInterfaceNames();
        return metadata.isInterface() && metadata.isIndependent()&& Arrays.asList(interfaceNames).contains(BaseApiProxy.class.getName());
    }

    /**
     * 扫描所有类
     */
    private void addFilter() {
        addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
    }

    /**
     * 为扫描到的接口创建代理对象
     *
     * @param beanDefinitionHolders beanDefinitionHolders
     */
    private void createBeanDefinition(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        for (var beanDefinitionHolder : beanDefinitionHolders) {
            var beanDefinition = ((GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition());
            //将bean的真实类型改变为FactoryBean
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
            beanDefinition.setBeanClass(ApiProxyFactoryBean.class);
            beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        }
    }

}
