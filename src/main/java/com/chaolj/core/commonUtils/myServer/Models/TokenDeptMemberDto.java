package com.chaolj.core.commonUtils.myServer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class TokenDeptMemberDto implements Serializable {
    @JsonProperty("AreaNo")
    @JSONField(name = "AreaNo")
    private String AreaNo;

    @JsonProperty("AreaName")
    @JSONField(name = "AreaName")
    private String AreaName;

    @JsonProperty("DeptId")
    @JSONField(name = "DeptId")
    private String DeptId;

    @JsonProperty("DeptNo")
    @JSONField(name = "DeptNo")
    private String DeptNo;

    @JsonProperty("DeptName")
    @JSONField(name = "DeptName")
    private String DeptName;

    @JsonProperty("DeptType")
    @JSONField(name = "DeptType")
    private String DeptType;

    @JsonProperty("DeptCityNo")
    @JSONField(name = "DeptCityNo")
    private String DeptCityNo;

    @JsonProperty("DeptCityName")
    @JSONField(name = "DeptCityName")
    private String DeptCityName;

    @JsonProperty("DeptProvinceNo")
    @JSONField(name = "DeptProvinceNo")
    private String DeptProvinceNo;

    @JsonProperty("DeptProvinceName")
    @JSONField(name = "DeptProvinceName")
    private String DeptProvinceName;

    @JsonProperty("JobNo")
    @JSONField(name = "JobNo")
    private String JobNo;

    @JsonProperty("JobName")
    @JSONField(name = "JobName")
    private String JobName;

    @JsonProperty("IsMainDept")
    @JSONField(name = "IsMainDept")
    private String IsMainDept;

    @JsonProperty("DeptLeader")
    @JSONField(name = "DeptLeader")
    private TokenDeptLeaderDto DeptLeader;

    @JsonProperty("DeptMaxLeader")
    @JSONField(name = "DeptMaxLeader")
    private TokenDeptLeaderDto DeptMaxLeader;
}
