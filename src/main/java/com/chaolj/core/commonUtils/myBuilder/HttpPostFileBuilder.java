package com.chaolj.core.commonUtils.myBuilder;

import cn.hutool.core.io.resource.BytesResource;
import cn.hutool.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HttpPostFileBuilder implements IHttpMethodBuilder {
    private HttpBuilder httpBuilder;
    private Map<String, Object> querys;
    private Map<String, Object> forms;
    private Map<String, BytesResource> files;

    public HttpPostFileBuilder(HttpBuilder httpBuilder) {
        this.httpBuilder = httpBuilder;
        this.querys = new HashMap<>();
        this.forms = new HashMap<>();
        this.files = new HashMap<>();
    }

    public HttpPostFileBuilder query(String key, Object value) {
        this.querys.put(key, value);
        return this;
    }

    public HttpPostFileBuilder query(Map<String, Object> querys) {
        this.querys.putAll(querys);
        return this;
    }

    public HttpPostFileBuilder form(String key, Object value) {
        this.forms.put(key, value);
        return this;
    }

    public HttpPostFileBuilder form(Map<String, Object> forms) {
        this.forms.putAll(forms);
        return this;
    }

    public HttpPostFileBuilder file(byte[] fileBytes, String fileName, String formName) {
        this.files.put(formName, new BytesResource(fileBytes, fileName));
        return this;
    }

    public HttpPostFileBuilder file(byte[] fileBytes, String fileName) {
        return this.file(fileBytes, fileName, "file");
    }

    public HttpRespBuilder execute() {
        var queryString = HttpBuilder.toQueryString(this.querys);
        var queryJoin = this.httpBuilder.getUrl().contains("?") ? "&" : "?";
        var url = this.httpBuilder.getUrl() + queryJoin + queryString;

        var request = HttpRequest.post(url)
                .headerMap(this.httpBuilder.getHeaders(), true)
                .timeout(this.httpBuilder.getTimeOut());

        for (var form : this.forms.entrySet()) {
            var name = form.getKey();
            var value = form.getValue();
            request.form(name, value);
        }

        for (var file : this.files.entrySet()) {
            var name = file.getKey();
            var resource = file.getValue();
            request.form(name, resource.readBytes(), resource.getName());
        }

        var resp = request.execute();

        var respBuilder = new HttpRespBuilder(this.httpBuilder, resp);
        return respBuilder;
    }
}
