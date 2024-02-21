package com.chaolj.core.commonUtils.myServer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class MailMessageDto implements Serializable {
    // 发件人，地址格式：address,displayName
    @JsonProperty("From")
    @JSONField(name = "From")
    private String From;

    // 收件人，地址格式：address1;address2;...
    @JsonProperty("To")
    @JSONField(name = "To")
    private String To;

    // 抄送，地址格式：address1;address2;...
    @JsonProperty("Cc")
    @JSONField(name = "Cc")
    private String Cc;

    // 密送，地址格式：address1;address2;...
    @JsonProperty("Bcc")
    @JSONField(name = "Bcc")
    private String Bcc;

    // 主题
    @JsonProperty("Subject")
    @JSONField(name = "Subject")
    private String Subject;

    // 内容
    @JsonProperty("Body")
    @JSONField(name = "Body")
    private String Body;

    // 是否Html格式
    @JsonProperty("IsBodyHtml")
    @JSONField(name = "IsBodyHtml")
    private Boolean IsBodyHtml;

    public static MailMessageDto getDefault() {
        var model = new MailMessageDto();
        model.setFrom("");
        model.setTo("");
        model.setSubject("");
        model.setBody("");
        model.setIsBodyHtml(false);

        return model;
    }
}
