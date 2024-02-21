package com.chaolj.core.commonUtils.myBuilder;

import cn.hutool.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HttpGetBuilder implements IHttpMethodBuilder {
    private HttpBuilder httpBuilder;
    private Map<String, Object> querys;

    public HttpGetBuilder(HttpBuilder httpBuilder) {
        this.httpBuilder = httpBuilder;
        this.querys = new HashMap<>();
    }

    public HttpGetBuilder query(String key, Object value) {
        this.querys.put(key, value);
        return this;
    }

    public HttpGetBuilder query(Map<String, Object> querys) {
        this.querys.putAll(querys);
        return this;
    }

    public HttpRespBuilder execute() {
        var queryString = HttpBuilder.toQueryString(this.querys);
        var queryJoin = this.httpBuilder.getUrl().contains("?") ? "&" : "?";
        var url = this.httpBuilder.getUrl() + queryJoin + queryString;

        var resp = HttpRequest.get(url)
                .headerMap(this.httpBuilder.getHeaders(), true)
                .timeout(this.httpBuilder.getTimeOut())
                .execute();

        var respBuilder = new HttpRespBuilder(this.httpBuilder, resp);
        return respBuilder;
    }
}
