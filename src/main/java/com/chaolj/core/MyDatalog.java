package com.chaolj.core;

import cn.hutool.core.util.StrUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.chaolj.core.commonUtils.myServer.Models.QueueMessageDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyDatalog {
    // 私有化构造函数
    private MyDatalog() {
    }

    public static MyDatalog Create() {
        var instance = new MyDatalog();
        instance._Logs = new ArrayList<>();

        return instance;
    }

    private List<QueueMessageDto> _Logs;

    public List<QueueMessageDto> getLogs() {
        return this._Logs;
    }

    /// <summary>
    /// 清除
    /// </summary>
    public void Clear() {
        this._Logs.clear();
    }

    /// <summary>
    /// 新增
    /// </summary>
    public void Add(QueueMessageDto log) {
        this._Logs.add(log);
    }

    /// <summary>
    /// 新增
    /// </summary>
    public void Add(String channel, String catalog, Object body) {
        if (body == null) return;

        var bodyJson = MyApp.Helper().JsonHelper().toJSONString(body);
        var message = new QueueMessageDto();
        message.setGId(MyApp.Helper().GuidHelper().NewID());
        message.setChannel(channel);
        message.setCatalog(catalog);
        message.setBody(bodyJson);
        message.setSubject(channel + "-" + catalog);
        message.setFrom(MyApp.Helper().ConfigHelper().getApplicationName());

        this._Logs.add(message);
    }

    /// <summary>
    /// 新增
    /// </summary>
    public <T> void Add(T model, String id, String opType, String opUser, String opComment) {
        var body = new HashMap<String, Object>();
        body.put("OpUser", opUser);
        body.put("OpType", opType);
        body.put("ModelId", id);
        body.put("Model", model);

        var bodyJson = MyApp.Helper().JsonHelper().toJSONString(body);
        var message = new QueueMessageDto();
        message.setGId(MyApp.Helper().GuidHelper().NewID());
        message.setChannel("DataLog");
        message.setCatalog(StrUtil.subAfter(model.getClass().toString(), "class ", false));
        message.setBody(bodyJson);
        message.setSubject(message.getChannel() + "-" + message.getCatalog() + "-" + opType);
        message.setFrom(MyApp.Helper().ConfigHelper().getApplicationName());

        this._Logs.add(message);
    }

    /// <summary>
    /// 新增
    /// </summary>
    public <T> void Add(T model, String id, String opType, String opUser) {
        this.Add(model, id, opType, opUser, "");
    }

    // 提交
    // 如果在全局事务中，则保存到缓存
    // 如果非全局事务，则推送到队列
    public void Commit() {
        var xid = MyUser.getCurrentGTTrackId();

        if (StrUtil.isBlank(xid)) {
            this.PushQueue();
        }
        else {
            this.SaveCache(xid);
        }
    }

    // 推送到队列
    private void PushQueue() {
        if (this._Logs == null || this._Logs.size() <= 0) return;

        var server = MyApp.Server().QueueServer(true);
        if (server == null) {
            log.info("MyDatalog.PushQueue，没有找到服务[QueueServer]的提供者，消息推送被忽略！");
            return;
        }

        try {
            var retrunDto = server.PushMessageList(this._Logs);
            if (!retrunDto.isResult()) {
                throw new RuntimeException("MyDatalog.PushQueue，失败！" + retrunDto.getMessage());
            }
        }
        catch (Exception ex) {
            throw new RuntimeException("MyDatalog.PushQueue，失败！" + ex.getMessage());
        }
    }

    // 保存到缓存
    private void SaveCache(String tranId) {
        if (this._Logs == null || this._Logs.size() <= 0) return;

        var server = MyApp.Server().CacheServer(true);
        if (server == null) {
            log.info("MyDatalog.SaveCache，没有找到服务[CacheServer]的提供者，操作被忽略！");
            return;
        }

        try {
            server.ListSet(tranId, this._Logs);
        }
        catch (Exception ex) {
            throw new RuntimeException("MyDatalog.SaveCache，失败！" + ex.getMessage());
        }
    }

    // 全局事务的首发者，负责最后提交缓存的数据日志
    public static void PushCache(String tranId) {
        var cacheServer = MyApp.Server().CacheServer(true);
        if (cacheServer == null) {
            log.info("MyDatalog.PushCache，没有找到服务[CacheServer]的提供者，操作被忽略！");
            return;
        }

        var queueServer = MyApp.Server().QueueServer(true);
        if (queueServer == null) {
            log.info("MyDatalog.PushCache，没有找到服务[QueueServer]的提供者，操作被忽略！");
            return;
        }

        try {
            var messages = cacheServer.<QueueMessageDto>ListGet(tranId);
            if (messages == null || messages.size() <= 0) return;

            var retrunDto = queueServer.PushMessageList(messages);
            if (!retrunDto.isResult()) {
                throw new RuntimeException(retrunDto.getMessage());
            }
        }
        catch (Exception ex) {
            throw new RuntimeException("MyDatalog.PushCache，失败！" + ex.getMessage());
        }
    }

    // 全局事务的首发者，负责最后清理缓存的数据日志
    public static void ClearCache(String tranId) {
        var cacheServer = MyApp.Server().CacheServer(true);
        if (cacheServer == null) {
            log.info("MyDatalog.ClearCache，没有找到服务[CacheServer]的提供者，操作被忽略！");
            return;
        }

        try {
            cacheServer.ListClear(tranId);
        }
        catch (Exception exception) {
            log.error("MyDatalog.ClearCache，失败！{}", exception.getMessage());
        }
    }
}
