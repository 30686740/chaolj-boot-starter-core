package com.chaolj.core.commonUtils.myServer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class WeChatTextMessageDto implements Serializable {
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

    // 文本内容
    @JsonProperty("text")
    @JSONField(name = "text")
    private WeChatTextMessageWrapDto text;

    public static WeChatTextMessageDto getDefault() {
        var model = new WeChatTextMessageDto();
        model.setAgentid(-1);
        model.setTouser("");
        model.setToparty("");
        model.setText(new WeChatTextMessageWrapDto());
        model.getText().setContent("");

        return model;
    }
}
