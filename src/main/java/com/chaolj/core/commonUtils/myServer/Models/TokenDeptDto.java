package com.chaolj.core.commonUtils.myServer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class TokenDeptDto implements Serializable {
    @JsonProperty("Id")
    @JSONField(name = "Id")
    private String Id;

    @JsonProperty("AreaNo")
    @JSONField(name = "AreaNo")
    private String AreaNo;

    @JsonProperty("AreaName")
    @JSONField(name = "AreaName")
    private String AreaName;

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
}
