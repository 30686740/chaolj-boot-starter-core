package com.chaolj.core.commonUtils.myServer;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import com.chaolj.core.commonUtils.myServer.Interface.*;

public class MyServerTemplate {
    private ApplicationContext applicationContext;
    private MyServerProperties properties;

    public MyServerTemplate(ApplicationContext applicationContext, MyServerProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    private <I> I getServerBean(String serverName, Class<I> clazz, boolean allowNull) {
        try {
            if (StrUtil.isBlank(serverName)) {
                var bean = this.applicationContext.getBean(clazz);
                if (bean == null && !allowNull) throw new RuntimeException("没有找到服务的提供者！");
                return bean;
            }
            else {
                var bean = this.applicationContext.getBean(serverName, clazz);
                if (bean == null && !allowNull) throw new RuntimeException("没有找到服务的提供者！");
                return bean;
            }
        }
        catch (BeansException ex) {
            throw new RuntimeException("加载" + clazz.getSimpleName() + "(" + serverName + ")失败！" + ex.getMessage());
        }
        catch (Exception ex) {
            throw new RuntimeException("加载" + clazz.getSimpleName() + "(" + serverName + ")失败！" + ex.getMessage());
        }
    }

    public IQueueServer QueueServer(String serverName, boolean allowNull) {
        return this.getServerBean(serverName, IQueueServer.class, allowNull);
    }

    public IQueueServer QueueServer(boolean allowNull) {
        return this.getServerBean(this.properties.getDefaultQueueServer(), IQueueServer.class, allowNull);
    }

    public IQueueServer QueueServer() {
        return this.getServerBean(this.properties.getDefaultQueueServer(), IQueueServer.class, false);
    }

    public ITokenServer TokenServer(String serverName, boolean allowNull) {
        return this.getServerBean(serverName, ITokenServer.class, allowNull);
    }

    public ITokenServer TokenServer(boolean allowNull) {
        return this.getServerBean(this.properties.getDefaultTokenServer(), ITokenServer.class, allowNull);
    }

    public ITokenServer TokenServer() {
        return this.getServerBean(this.properties.getDefaultTokenServer(), ITokenServer.class, false);
    }

    public ICacheServer CacheServer(String serverName, boolean allowNull) {
        return this.getServerBean(serverName, ICacheServer.class, allowNull);
    }

    public ICacheServer CacheServer(boolean allowNull) {
        return this.getServerBean(this.properties.getDefaultCacheServer(), ICacheServer.class, allowNull);
    }

    public ICacheServer CacheServer() {
        return this.getServerBean(this.properties.getDefaultCacheServer(), ICacheServer.class, false);
    }

    public IElasticsearchServer ElasticsearchServer(String serverName, boolean allowNull) {
        return this.getServerBean(serverName, IElasticsearchServer.class, allowNull);
    }

    public IElasticsearchServer ElasticsearchServer(boolean allowNull) {
        return this.getServerBean(this.properties.getDefaultElasticsearchServer(), IElasticsearchServer.class, allowNull);
    }

    public IElasticsearchServer ElasticsearchServer() {
        return this.getServerBean(this.properties.getDefaultElasticsearchServer(), IElasticsearchServer.class, false);
    }

    public IExcelServer ExcelServer(String serverName, boolean allowNull) {
        return this.getServerBean(serverName, IExcelServer.class, allowNull);
    }

    public IExcelServer ExcelServer(boolean allowNull) {
        return this.getServerBean(this.properties.getDefaultExcelServer(), IExcelServer.class, allowNull);
    }

    public IExcelServer ExcelServer() {
        return this.getServerBean(this.properties.getDefaultExcelServer(), IExcelServer.class, false);
    }

    public IFileServer FileServer(String serverName, boolean allowNull) {
        return this.getServerBean(serverName, IFileServer.class, allowNull);
    }

    public IFileServer FileServer(boolean allowNull) {
        return this.getServerBean(this.properties.getDefaultFileServer(), IFileServer.class, allowNull);
    }

    public IFileServer FileServer() {
        return this.getServerBean(this.properties.getDefaultFileServer(), IFileServer.class, false);
    }

    public IGlobalServer GlobalServer(String serverName, boolean allowNull) {
        return this.getServerBean(serverName, IGlobalServer.class, allowNull);
    }

    public IGlobalServer GlobalServer(boolean allowNull) {
        return this.getServerBean(this.properties.getDefaultGlobalServer(), IGlobalServer.class, allowNull);
    }

    public IGlobalServer GlobalServer() {
        return this.getServerBean(this.properties.getDefaultGlobalServer(), IGlobalServer.class, false);
    }
}
