package com.chaolj.core.commonUtils.myServer.Interface;

import com.chaolj.core.commonUtils.myDelegate.ActionDelegate;
import java.util.Collection;
import java.util.List;

/*
 * 分布式缓存服务接口
 * */
public interface ICacheServer {
    <T> void ListSet(String key, Collection<T> list, long seconds);

    <T> void ListSet(String key, Collection<T> list);

    <T> List<T> ListGet(String key);

    <T> void ListRemove(String key, Collection<T> list);

    void ListClear(String key);

    boolean GlobalHasKey(String key);

    Object GlobalGet(String key);

    void GlobalSet(String key, Object value, long seconds);

    void GlobalSet(String key, Object value);

    void GlobalRemove(String... key);

    boolean AppHasKey(String key);

    Object AppGet(String key);

    void AppSet(String key, Object value, long seconds);

    void AppSet(String key, Object value);

    void AppRemove(String... key);

    void RunWithLock(String key, ActionDelegate action);

    void RunWithLock(List<String> keys, ActionDelegate action);
}
