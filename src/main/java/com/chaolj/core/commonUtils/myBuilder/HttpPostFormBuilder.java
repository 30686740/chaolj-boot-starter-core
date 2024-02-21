package com.chaolj.core.commonUtils.myBuilder;

import cn.hutool.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HttpPostFormBuilder implements IHttpMethodBuilder {
    private HttpBuilder httpBuilder;
    private Map<String, Object> querys;
    private Map<String, Object> postdata;
    private String contentType;

    public HttpPostFormBuilder(HttpBuilder httpBuilder) {
        this.httpBuilder = httpBuilder;
        this.querys = new HashMap<>();
        this.postdata = new HashMap<>();
    }

    public HttpPostFormBuilder query(String key, Object value) {
        this.querys.put(key, value);
        return this;
    }

    public HttpPostFormBuilder query(Map<String, Object> querys) {
        this.querys.putAll(querys);
        return this;
    }

    public HttpPostFormBuilder form(String key, Object value) {
        this.postdata.put(key, value);
        return this;
    }

    public HttpPostFormBuilder form(Map<String, Object> formdata) {
        this.postdata.putAll(formdata);
        return this;
    }

    public HttpPostFormBuilder contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public HttpRespBuilder execute() {
        var queryString = HttpBuilder.toQueryString(this.querys);
        var queryJoin = this.httpBuilder.getUrl().contains("?") ? "&" : "?";
        var url = this.httpBuilder.getUrl() + queryJoin + queryString;

        var resp = HttpRequest.post(url)
                .headerMap(this.httpBuilder.getHeaders(), true)
                .timeout(this.httpBuilder.getTimeOut())
                .form(this.postdata)
                .execute();

        var respBuilder = new HttpRespBuilder(this.httpBuilder, resp);
        return respBuilder;
    }
}
