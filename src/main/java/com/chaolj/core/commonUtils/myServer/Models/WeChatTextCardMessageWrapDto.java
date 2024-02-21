package com.chaolj.core.commonUtils.myServer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class WeChatTextCardMessageWrapDto implements Serializable {
    // 标题
    @JsonProperty("title")
    @JSONField(name = "title")
    private String title;

    // 描述
    @JsonProperty("description")
    @JSONField(name = "description")
    private String description;

    // 按钮文字
    @JsonProperty("btntxt")
    @JSONField(name = "btntxt")
    private String btntxt;

    // 跳转地址
    @JsonProperty("url")
    @JSONField(name = "url")
    private String url;
}
