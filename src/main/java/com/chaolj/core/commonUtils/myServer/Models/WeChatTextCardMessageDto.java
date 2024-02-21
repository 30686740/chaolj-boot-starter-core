package com.chaolj.core.commonUtils.myServer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class WeChatTextCardMessageDto implements Serializable {
    // 应用ID，默认-1
    @JsonProperty("agentid")
    @JSONField(name = "agentid")
    private Integer agentid = -1;

    // 收件人（用户名），地址格式：loginname1,loginname2,...
    @JsonProperty("touser")
    @JSONField(name = "touser")
    private String touser;

    // 收件人（部门），地址格式：deptname1,deptname2,...
    @JsonProperty("toparty")
    @JSONField(name = "toparty")
    private String toparty;

    // 卡片内容
    @JsonProperty("textcard")
    @JSONField(name = "textcard")
    private WeChatTextCardMessageWrapDto textcard;

    public static WeChatTextCardMessageDto getDefault() {
        var model = new WeChatTextCardMessageDto();
        model.setAgentid(-1);
        model.setTouser("");
        model.setToparty("");
        model.setTextcard(new WeChatTextCardMessageWrapDto());
        model.getTextcard().setTitle("");
        model.getTextcard().setDescription("");
        model.getTextcard().setBtntxt("");
        model.getTextcard().setUrl("");

        return model;
    }
}
