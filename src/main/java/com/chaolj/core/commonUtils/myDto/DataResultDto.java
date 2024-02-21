package com.chaolj.core.commonUtils.myDto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class DataResultDto<T> implements Serializable {
    @ApiModelProperty("返回结果")
    @JsonProperty("Result")
    @JSONField(name = "Result")
    private boolean Result;

    @ApiModelProperty("返回代码")
    @JsonProperty("Code")
    @JSONField(name = "Code")
    private String Code;

    @ApiModelProperty("返回消息")
    @JsonProperty("Message")
    @JSONField(name = "Message")
    private String Message;

    @ApiModelProperty("返回数据")
    @JsonProperty("Data")
    @JSONField(name = "Data")
    private T Data;

    public <T1> DataResultDto<T1> toJavaObject(Class<T1> clazz){
        var newResult = DataResultDto.<T1>Empty();
        newResult.setResult(this.isResult());
        newResult.setCode(this.getCode());
        newResult.setMessage(this.getMessage());

        if (this.getData() instanceof JSONObject){
            var jsonObject = (JSONObject)this.getData();
            newResult.setData(jsonObject.toJavaObject(clazz));
        }

        return newResult;
    }

    public <T1> DataResultDto<List<T1>> toJavaList(Class<T1> clazz){
        var newResult = DataResultDto.<List<T1>>Empty();
        newResult.setResult(this.isResult());
        newResult.setCode(this.getCode());
        newResult.setMessage(this.getMessage());

        if (this.getData() instanceof JSONArray){
            var jsonArray = (JSONArray)this.getData();
            newResult.setData(jsonArray.toJavaList(clazz));
        }

        return newResult;
    }

    public static <T> DataResultDto<T> Empty() {
        var result = new DataResultDto<T>();
        result.setResult(false);
        result.setCode("");
        result.setMessage("");
        result.setData(null);

        return result;
    }

    public static <T> DataResultDto<T> Ok(T data) {
        var result = new DataResultDto<T>();
        result.setResult(true);
        result.setCode("");
        result.setMessage("操作成功！");
        result.setData(data);

        return result;
    }

    public static <T> DataResultDto<T> Ok() {
        return Ok(null);
    }

    public static <T> DataResultDto<T> error(String errmsg) {
        var result = new DataResultDto<T>();
        result.setResult(false);
        result.setCode("");
        result.setMessage("操作失败！" + errmsg);
        result.setData(null);

        return result;
    }
}
