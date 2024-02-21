package com.chaolj.core.commonUtils.myServer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class GlobalOptionDto implements Serializable {
    @JsonProperty("Id")
    @JSONField(name = "Id")
    private String Id;

    @JsonProperty("OptionCatalog")
    @JSONField(name = "OptionCatalog")
    private String OptionCatalog;

    @JsonProperty("OptionOrder")
    @JSONField(name = "OptionOrder")
    private Integer OptionOrder;

    @JsonProperty("Name")
    @JSONField(name = "Name")
    private String Name;

    @JsonProperty("Value")
    @JSONField(name = "Value")
    private String Value;

    @JsonProperty("Comment")
    @JSONField(name = "Comment")
    private String Comment;
}
