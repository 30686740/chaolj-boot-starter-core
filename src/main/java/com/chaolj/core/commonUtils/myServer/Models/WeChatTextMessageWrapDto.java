package com.chaolj.core.commonUtils.myServer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class WeChatTextMessageWrapDto implements Serializable {
    // 描述
    @JsonProperty("content")
    @JSONField(name = "content")
    private String content;
}
