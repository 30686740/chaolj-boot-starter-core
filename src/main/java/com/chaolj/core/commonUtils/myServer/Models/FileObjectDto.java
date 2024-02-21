package com.chaolj.core.commonUtils.myServer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class FileObjectDto implements Serializable {
    @JsonProperty("Path")
    @JSONField(name = "Path")
    private String Path;

    @JsonProperty("FileName")
    @JSONField(name = "FileName")
    private String FileName;

    @JsonProperty("FileSize")
    @JSONField(name = "FileSize")
    private Long FileSize;

    @JsonProperty("CreateTime")
    @JSONField(name = "CreateTime")
    private LocalDateTime CreateTime;
}
