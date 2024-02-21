package com.chaolj.core.commonUtils.myBuilder;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import com.chaolj.core.MyApp;
import com.chaolj.core.commonUtils.myServer.Models.QueueMessageDto;

@Slf4j
public class LogInfoBuilder {
    private String from;
    private String channel;
    private String catalog;
    private String body;

    public LogInfoBuilder() {
        this.channel = "InfoLog";
        this.catalog = MyApp.Helper().ConfigHelper().getApplicationName();
    }

    public LogInfoBuilder From(String value) {
        this.from = value;
        return this;
    }

    public LogInfoBuilder Body(String value) {
        var content = new HashMap<>();
        content.put("Content", value);

        this.body = JSONObject.toJSONString(content);
        return this;
    }

    public void Push() {
        try {
            var server = MyApp.Server().QueueServer(true);
            if (server == null) {
                log.info("LogInfoBuilder.Push，没有找到服务[QueueServer]的提供者，消息推送被忽略！");
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
                log.error("LogInfoBuilder.Push，失败！{}，{}，{}，{}，{}", retrunDto.getMessage(), this.channel, this.catalog, this.from, this.body);
            }
        }
        catch (Exception ex)
        {
            log.error("LogInfoBuilder.Push，失败！{}，{}，{}，{}，{}", ex.getMessage(), this.channel, this.catalog, this.from, this.body);
        }
    }
}
