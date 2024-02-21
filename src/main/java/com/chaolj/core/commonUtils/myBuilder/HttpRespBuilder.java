package com.chaolj.core.commonUtils.myBuilder;

import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.chaolj.core.MyApp;

import java.util.List;
import java.util.Optional;

public class HttpRespBuilder {
    private HttpBuilder httpBuilder;
    private HttpResponse httpResponse;

    public HttpRespBuilder(HttpBuilder httpBuilder, HttpResponse httpResponse) {
        this.httpBuilder = httpBuilder;
        this.httpResponse = httpResponse;
    }

    public HttpResponse getHttpResponse() {
        return this.httpResponse;
    }

    public int getStatus() {
        return this.httpResponse.getStatus();
    }

    public String getBodyString() {
        return this.httpResponse.body();
    }

    public byte[] getBodyBytes() {
        return this.httpResponse.bodyBytes();
    }

    public JSONObject toJsonObject() {
        try {
            return MyApp.Helper().JsonHelper().parseObject(this.httpResponse.body());
        }
        catch (Exception ex) {
            var url = Optional.ofNullable(this.httpBuilder.getUrl()).orElse("");
            var body = Optional.ofNullable(this.httpResponse.body()).orElse("");
            if (body.length() > 200) body = body.substring(0, 200) + " ...";

            var message = "HttpRespBuilder.toJsonObject,处理失败！"
                    + System.lineSeparator() + "url = " + url
                    + System.lineSeparator() + "body = " + body
                    + System.lineSeparator() + ex.toString();
            MyApp.Logger().error(message);

            throw new RuntimeException(ex.getMessage());
        }
    }

    public JSONArray toJsonArray() {
        try {
            return MyApp.Helper().JsonHelper().parseArray(this.httpResponse.body());
        }
        catch (Exception ex) {
            var url = Optional.ofNullable(this.httpBuilder.getUrl()).orElse("");
            var body = Optional.ofNullable(this.httpResponse.body()).orElse("");
            if (body.length() > 200) body = body.substring(0, 200) + " ...";

            var message = "HttpRespBuilder.toJsonArray,处理失败！"
                    + System.lineSeparator() + "url = " + url
                    + System.lineSeparator() + "body = " + body
                    + System.lineSeparator() + ex.toString();
            MyApp.Logger().error(message);

            throw new RuntimeException(ex.getMessage());
        }
    }

    public <T> T toJavaObject(Class<T> tClass, Feature... features) {
        try {
            return MyApp.Helper().JsonHelper().parseObject(this.httpResponse.body(), tClass, features);
        }
        catch (Exception ex) {
            var url = Optional.ofNullable(this.httpBuilder.getUrl()).orElse("");
            var body = Optional.ofNullable(this.httpResponse.body()).orElse("");
            if (body.length() > 200) body = body.substring(0, 200) + " ...";

            var message = "HttpRespBuilder.toJavaObject,处理失败！"
                    + System.lineSeparator() + "url = " + url
                    + System.lineSeparator() + "body = " + body
                    + System.lineSeparator() + ex.toString();
            MyApp.Logger().error(message);

            throw new RuntimeException(ex.getMessage());
        }
    }

    public <T> T toJavaObject(Class<T> tClass) {
        try {
            return MyApp.Helper().JsonHelper().parseObject(this.httpResponse.body(), tClass);
        }
        catch (Exception ex) {
            var url = Optional.ofNullable(this.httpBuilder.getUrl()).orElse("");
            var body = Optional.ofNullable(this.httpResponse.body()).orElse("");
            if (body.length() > 200) body = body.substring(0, 200) + " ...";

            var message = "HttpRespBuilder.toJavaObject,处理失败！"
                    + System.lineSeparator() + "url = " + url
                    + System.lineSeparator() + "body = " + body
                    + System.lineSeparator() + ex.toString();
            MyApp.Logger().error(message);

            throw new RuntimeException(ex.getMessage());
        }
    }

    public <T> T toJavaObject(TypeReference<T> type, Feature... features) {
        try {
            return MyApp.Helper().JsonHelper().parseObject(this.httpResponse.body(), type, features);
        }
        catch (Exception ex) {
            var url = Optional.ofNullable(this.httpBuilder.getUrl()).orElse("");
            var body = Optional.ofNullable(this.httpResponse.body()).orElse("");
            if (body.length() > 200) body = body.substring(0, 200) + " ...";

            var message = "HttpRespBuilder.toJavaObject,处理失败！"
                    + System.lineSeparator() + "url = " + url
                    + System.lineSeparator() + "body = " + body
                    + System.lineSeparator() + ex.toString();
            MyApp.Logger().error(message);

            throw new RuntimeException(ex.getMessage());
        }
    }

    public <T> T toJavaObject(TypeReference<T> type) {
        try {
            return MyApp.Helper().JsonHelper().parseObject(this.httpResponse.body(), type);
        }
        catch (Exception ex) {
            var url = Optional.ofNullable(this.httpBuilder.getUrl()).orElse("");
            var body = Optional.ofNullable(this.httpResponse.body()).orElse("");
            if (body.length() > 200) body = body.substring(0, 200) + " ...";

            var message = "HttpRespBuilder.toJavaObject,处理失败！"
                    + System.lineSeparator() + "url = " + url
                    + System.lineSeparator() + "body = " + body
                    + System.lineSeparator() + ex.toString();
            MyApp.Logger().error(message);

            throw new RuntimeException(ex.getMessage());
        }
    }

    public <T> List<T> toJavaArray(Class<T> tClass, ParserConfig config) {
        try {
            return MyApp.Helper().JsonHelper().parseArray(this.httpResponse.body(), tClass, config);
        }
        catch (Exception ex) {
            var url = Optional.ofNullable(this.httpBuilder.getUrl()).orElse("");
            var body = Optional.ofNullable(this.httpResponse.body()).orElse("");
            if (body.length() > 200) body = body.substring(0, 200) + " ...";

            var message = "HttpRespBuilder.toJavaArray,处理失败！"
                    + System.lineSeparator() + "url = " + url
                    + System.lineSeparator() + "body = " + body
                    + System.lineSeparator() + ex.toString();
            MyApp.Logger().error(message);

            throw new RuntimeException(ex.getMessage());
        }
    }

    public <T> List<T> toJavaArray(Class<T> tClass) {
        try {
            return MyApp.Helper().JsonHelper().parseArray(this.httpResponse.body(), tClass);
        }
        catch (Exception ex) {
            var url = Optional.ofNullable(this.httpBuilder.getUrl()).orElse("");
            var body = Optional.ofNullable(this.httpResponse.body()).orElse("");
            if (body.length() > 200) body = body.substring(0, 200) + " ...";

            var message = "HttpRespBuilder.toJavaArray,处理失败！"
                    + System.lineSeparator() + "url = " + url
                    + System.lineSeparator() + "body = " + body
                    + System.lineSeparator() + ex.toString();
            MyApp.Logger().error(message);

            throw new RuntimeException(ex.getMessage());
        }
    }
}
