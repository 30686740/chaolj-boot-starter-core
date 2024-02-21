package com.chaolj.core.commonUtils.myBuilder;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import com.chaolj.core.MyApp;
import com.chaolj.core.MyUser;
import com.chaolj.core.commonUtils.myServer.Models.QueueMessageDto;

@Slf4j
public class LogErrBuilder {
    private String from;
    private String channel;
    private String catalog;
    private String body;

    public LogErrBuilder() {
        this.channel = "ErrLog";
        this.catalog = MyApp.Helper().ConfigHelper().getApplicationName();
    }

    public LogErrBuilder From(String value) {
        this.from = value;
        return this;
    }

    public LogErrBuilder Body(String value) {
        var content = new HashMap<>();
        content.put("Content", value);

        this.body = JSONObject.toJSONString(content);
        return this;
    }

    public LogErrBuilder Body(Exception ex) {
        var content = new HashMap<>();
        content.put("Content", ex.getMessage());

        this.body = JSONObject.toJSONString(content);
        return this;
    }

    public LogErrBuilder Body(Exception ex, HttpServletRequest request) {
        var content = new LinkedHashMap<String, String>();
        content.put("requestUrl", request.getRequestURI());
        content.put("requestQueryString", request.getQueryString());
        content.put("requestMethod", request.getMethod());
        content.put("requestBody", MyApp.Helper().ServletHelper().GetRequestBody(request));
        content.put("requestIP", MyApp.Helper().ServletHelper().GetRequestIp(request));
        content.put("requestUserAgent", request.getHeader("User-Agent"));
        content.put("UserToken", MyUser.getCurrentUserToken());
        content.put("UserName", MyUser.getCurrentUserName());
        content.put("HttpTrackId", MyUser.getCurrentHttpTrackId());
        content.put("Message", ex.getMessage());
        this.body = JSONObject.toJSONString(content, SerializerFeature.WriteMapNullValue);
        return this;
    }

    public void Push() {
        try {
            var server = MyApp.Server().QueueServer(true);
            if (server == null) {
                log.info("LogErrBuilder.Push，没有找到服务[QueueServer]的提供者，消息推送被忽略！");
                return;
            }

            var messageDto = new QueueMessageDto();
            messageDto.setGId(MyApp.Helper().GuidHelper().NewID());
            messageDto.setChannel(this.channel);
            messageDto.setCatalog(this.catalog);
            messageDto.setSubject(messageDto.getChannel() + "-" + messageDto.getCatalog());
            messageDto.setFrom(this.from);
            messageDto.setBody(this.body);

            var retrunDto = server.PushMessage(messageDto);
            if (!retrunDto.isResult()) {
                log.error("LogErrBuilder.Push，失败！{}，{}，{}，{}，{}", retrunDto.getMessage(), this.channel, this.catalog, this.from, this.body);
            }
        }
        catch (Exception ex) {
            log.error("LogErrBuilder.Push，失败！{}，{}，{}，{}，{}", ex.getMessage(), this.channel, this.catalog, this.from, this.body);
        }
    }
}