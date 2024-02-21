package com.chaolj.core.commonUtils.myBuilder;

import cn.hutool.http.HttpRequest;
import com.chaolj.core.MyApp;

import java.util.HashMap;
import java.util.Map;

public class HttpPostObjectBuilder implements IHttpMethodBuilder {
    private HttpBuilder httpBuilder;
    private Map<String, Object> querys;
    private Object postdata;

    public HttpPostObjectBuilder(HttpBuilder httpBuilder) {
        this.httpBuilder = httpBuilder;
        this.querys = new HashMap<>();
    }

    public HttpPostObjectBuilder query(String key, Object value) {
        this.querys.put(key, value);
        return this;
    }

    public HttpPostObjectBuilder query(Map<String, Object> querys) {
        this.querys.putAll(querys);
        return this;
    }

    public HttpPostObjectBuilder body(Object postdata) {
        this.postdata = postdata;
        return this;
    }

    public HttpRespBuilder execute() {
        var queryString = HttpBuilder.toQueryString(this.querys);
        var queryJoin = this.httpBuilder.getUrl().contains("?") ? "&" : "?";
        var posturl = this.httpBuilder.getUrl() + queryJoin + queryString;
        var poststring = MyApp.Helper().JsonHelper().toJSONString(this.postdata);
        var postContentType = "application/json";

        var resp = HttpRequest.post(posturl)
                .headerMap(this.httpBuilder.getHeaders(), true)
                .timeout(this.httpBuilder.getTimeOut())
                .body(poststring, postContentType)
                .execute();

        var respBuilder = new HttpRespBuilder(this.httpBuilder, resp);
        return respBuilder;
    }
}
