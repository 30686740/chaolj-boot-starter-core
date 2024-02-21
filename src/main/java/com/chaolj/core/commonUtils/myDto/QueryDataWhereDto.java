package com.chaolj.core.commonUtils.myDto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel
public class QueryDataWhereDto implements Serializable {
    @ApiModelProperty("字段名")
    @JsonProperty("Field")
    @JSONField(name = "Field")
    private String Field;

    @ApiModelProperty("逻辑符（=, <>, >, >=, <, <=, Like, StartsWith, EndsWith, In）")
    @JsonProperty("Operation")
    @JSONField(name = "Operation")
    private String Operation;

    @ApiModelProperty("值（使用In时，多个值用逗号分隔）")
    @JsonProperty("Value")
    @JSONField(name = "Value")
    private String Value;
}
