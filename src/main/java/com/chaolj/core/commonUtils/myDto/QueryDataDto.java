package com.chaolj.core.commonUtils.myDto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel
public class QueryDataDto implements Serializable {
    @ApiModelProperty("查询条件（与）")
    @JsonProperty("WhereAnd")
    @JSONField(name = "WhereAnd")
    private List<QueryDataWhereDto> WhereAnd;

    @ApiModelProperty("查询条件（或）")
    @JsonProperty("WhereOr")
    @JSONField(name = "WhereOr")
    private List<QueryDataWhereDto> WhereOr;

    @ApiModelProperty("排序（StartTime1 Asc, StartTime2 Desc）")
    @JsonProperty("Sort")
    @JSONField(name = "Sort")
    private String Sort;

    @ApiModelProperty("页码，默认1")
    @JsonProperty("Page")
    @JSONField(name = "Page")
    private int Page;

    @ApiModelProperty("每页行数，默认20")
    @JsonProperty("Rows")
    @JSONField(name = "Rows")
    private int Rows;

    public void AppendWhereAnd(String field, QueryDataWhereOpDto operation, String value){
        if (this.WhereAnd == null) this.WhereAnd = new ArrayList<>();

        var where = new QueryDataWhereDto();
        where.setField(field);
        where.setOperation(operation.getName());
        where.setValue(value);
        this.WhereAnd.add(where);
    }

    public void AppendWhereOr(String field, QueryDataWhereOpDto operation, String value){
        if (this.WhereOr == null) this.WhereOr = new ArrayList<>();

        var where = new QueryDataWhereDto();
        where.setField(field);
        where.setOperation(operation.getName());
        where.setValue(value);
        this.WhereOr.add(where);
    }
}
