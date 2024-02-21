package com.chaolj.core.commonUtils.myServer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.chaolj.core.MyApp;
import lombok.Data;
import java.io.Serializable;

@Data
public class QueueMessageDto implements Serializable {
    @JsonProperty("GId")
    @JSONField(name = "GId")
    private String GId;

    @JsonProperty("Channel")
    @JSONField(name = "Channel")
    private String Channel;

    @JsonProperty("Catalog")
    @JSONField(name = "Catalog")
    private String Catalog;

    @JsonProperty("From")
    @JSONField(name = "From")
    private String From;

    @JsonProperty("Subject")
    @JSONField(name = "Subject")
    private String Subject;

    @JsonProperty("Body")
    @JSONField(name = "Body")
    private String Body;

    public static QueueMessageDto getDefault() {
        var model = new QueueMessageDto();
        model.setGId(MyApp.Helper().GuidHelper().NewID());
        model.setChannel("default");
        model.setCatalog("default");
        model.setFrom("");
        model.setSubject("");
        model.setBody("{}");

        return model;
    }
}
