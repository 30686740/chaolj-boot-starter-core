package com.chaolj.core.commonUtils.myBuilder;

import cn.hutool.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HttpPostBuilder implements IHttpMethodBuilder {
    private HttpBuilder httpBuilder;
    private Map<String, Object> querys;
    private String postdata;
    private String contentType;

    public HttpPostBuilder(HttpBuilder httpBuilder) {
        this.httpBuilder = httpBuilder;
        this.querys = new HashMap<>();
    }

    public HttpPostBuilder query(String key, Object value) {
        this.querys.put(key, value);
        return this;
    }

    public HttpPostBuilder query(Map<String, Object> querys) {
        this.querys.putAll(querys);
        return this;
    }

    public HttpPostBuilder body(String postdata) {
        this.postdata = postdata;
        return this;
    }

    public HttpPostBuilder contentType(String contentType) {
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
                .body(this.postdata, this.contentType)
                .execute();

        var respBuilder = new HttpRespBuilder(this.httpBuilder, resp);
        return respBuilder;
    }
}
