package com.chaolj.core.commonUtils.myServer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class TokenAppRoleFuncDto implements Serializable {
    @JsonProperty("Code")
    @JSONField(name = "Code")
    private String Code;

    @JsonProperty("DisplayName")
    @JSONField(name = "DisplayName")
    private String DisplayName;
}
