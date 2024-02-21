package com.chaolj.core.commonUtils.myServer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class TokenDeptLeaderDto implements Serializable {
    @JsonProperty("EmpNo")
    @JSONField(name = "EmpNo")
    private String EmpNo;

    @JsonProperty("EmpLoginName")
    @JSONField(name = "EmpLoginName")
    private String EmpLoginName;

    @JsonProperty("EmpName")
    @JSONField(name = "EmpName")
    private String EmpName;
}
