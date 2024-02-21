package com.chaolj.core.commonUtils.myServer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class TokenAppRoleDto implements Serializable {
    @JsonProperty("RoleNo")
    @JSONField(name = "RoleNo")
    private String RoleNo;

    @JsonProperty("RoleName")
    @JSONField(name = "RoleName")
    private String RoleName;

    @JsonProperty("Functions")
    @JSONField(name = "Functions")
    private List<TokenAppRoleFuncDto> Functions;
}
