package com.chaolj.core.commonUtils.myDto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class PageResultDto<T> implements Serializable {
    @ApiModelProperty("总数")
    @JsonProperty("Count")
    @JSONField(name = "Count")
    private long Count;

    @ApiModelProperty("当前页数据")
    @JsonProperty("Rows")
    @JSONField(name = "Rows")
    private List<T> Rows;
}
