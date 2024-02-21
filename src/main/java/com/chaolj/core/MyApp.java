package com.chaolj.core;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaolj.core.bootUtils.bootConfig.AppContextAware;
import com.chaolj.core.commonUtils.myBuilder.OfLocalDateBuilder;
import com.chaolj.core.commonUtils.myBuilder.OfLocalDateTimeBuilder;
import com.chaolj.core.commonUtils.myBuilder.OfTimestampBuilder;
import com.chaolj.core.commonUtils.myDto.QueryDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;
import com.chaolj.core.commonUtils.myHelper.MyHelperTemplate;
import com.chaolj.core.commonUtils.myServer.MyServerTemplate;
import com.chaolj.core.commonUtils.myBuilder.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/*
* 应用程序常用工具方法入口
*/
public class MyApp {
    /*
    * Spring Application上下文对象
    * */
    public static ApplicationContext Context() { return AppContextAware.getInstance(); }

    /*
    * Spring Web请求上下文对象
    * */
    public static ServletRequestAttributes WebContext() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /*
    * 通过名称，获取Spring IOC中的Bean对象
    * */
    public static <T> T MyBean(String beanName) { return (T) AppContextAware.getInstance().getBean(beanName); }

    /*
     * 通过类型，获取Spring IOC中的Bean对象
     * */
    public static <T> T MyBean(Class<T> clazz) { return AppContextAware.getInstance().getBean(clazz); }

    /*
     * 通过类型和名称，获取Spring IOC中的Bean对象
     * */
    public static <T> T MyBean(Class<T> clazz, String beanName) { return AppContextAware.getInstance().getBean(beanName, clazz); }

    /*
     * Spring 本地事务对象
     * */
    public static TransactionTemplate Trans() { return AppContextAware.getInstance().getBean(TransactionTemplate.class); }

    /*
     * 自定义的帮助工具方法
     * */
    public static MyHelperTemplate Helper() { return AppContextAware.getInstance().getBean(MyHelperTemplate.class); }

    /*
     * 自定义的服务提供者
     * */
    public static MyServerTemplate Server() { return AppContextAware.getInstance().getBean(MyServerTemplate.class); }

    // region Of Builder

    public static OfDateBuilder Of(Date data) { return new OfDateBuilder(data); }

    public static OfTimestampBuilder Of(Timestamp data) { return new OfTimestampBuilder(data); }

    public static OfLocalDateBuilder Of(LocalDate data) { return new OfLocalDateBuilder(data); }

    public static OfLocalDateTimeBuilder Of(LocalDateTime data) { return new OfLocalDateTimeBuilder(data); }

    public static OfStringBuilder Of(String data) { return new OfStringBuilder(data); }

    public static OfStringArrayBuilder Of(String[] data) { return new OfStringArrayBuilder(data); }

    public static OfStringListBuilder Of(List<String> data) { return new OfStringListBuilder(data); }

    public static OfMapBuilder Of(Map<String, Object> data) { return new OfMapBuilder(data); }

    public static OfMapListBuilder OfList(List<Map<String, Object>> data) { return new OfMapListBuilder(data); }

    // endregion

    // region Query Builder

    public static <TPO> LambdaQueryBuilder<TPO> Query(Class<? extends BaseMapper<TPO>> mapperClazz) {
        return LambdaQueryBuilder.Builder(mapperClazz);
    }

    public static XmlQueryBuilder Query() {
        return XmlQueryBuilder.Builder();
    }

    public static XmlQueryBuilder Query(QueryDataDto data) {
        return XmlQueryBuilder.Builder(data);
    }

    // endregion

    // region Log Builder

    /*
     * 一般记录类型的日志操作对象，会推送到消息队列
     * */
    public static LogInfoBuilder LogInfo() {
        return new LogInfoBuilder();
    }

    /*
     * 错误类型的日志操作对象，会推送到消息队列
     * */
    public static LogErrBuilder LogErr() {
        return new LogErrBuilder();
    }

    /*
     * 访问跟踪类型的日志操作对象，会推送到消息队列
     * */
    public static LogAccessBuilder LogAccess() {
        return new LogAccessBuilder();
    }

    /*
     * Spring 本地日志对象
     * */
    public static Logger Logger() {
        return LoggerFactory.getLogger(MyApp.class);
    }

    // endregion

    // region Other Builder

    /*
     * 数据连接包装对象，操作被@Primary注解的数据源
     * */
    public static DBConnectionBuilder DBConnection()  {
        return DBConnectionBuilder.Builder();
    }

    /*
     * 数据连接包装对象，操作指定名称的数据源
     * */
    public static DBConnectionBuilder DBConnection(String dataSourceName)  {
        return DBConnectionBuilder.Builder(dataSourceName);
    }

    /*
     * Spring Http请求包装类
     * */
    public static HttpBuilder Http()  {
        return HttpBuilder.Builder();
    }

    /*
     * 多线程操作包装类
     * */
    public static ThreadBuilder Thread() {
        return ThreadBuilder.Builder();
    }

    // endregion
}
