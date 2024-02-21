package com.chaolj.core.commonUtils.myBuilder;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.chaolj.core.MyApp;
import com.chaolj.core.MyConst;
import com.chaolj.core.MyUser;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpBuilder {
    private String url;
    private int timeOut;
    private String defUserClient;
    private String defUserName;
    private Map<String, String> headers;

    private HttpBuilder() { }

    public static HttpBuilder Builder() {
        var self = new HttpBuilder();
        self.url = "blank:about";
        self.timeOut = 10000;
        self.defUserClient = "001";
        self.defUserName = "dev";
        self.headers = new HashMap<>();

        if (StrUtil.isNotBlank(MyUser.getCurrentHttpTrackId())) {
            self.headers.put(MyConst.HEADERKEY_HttpTrackID, MyUser.getCurrentHttpTrackId());
        }

        if (StrUtil.isNotBlank(MyUser.getCurrentGTTrackId())) {
            self.headers.put(MyConst.HEADERKEY_GTTrackID, MyUser.getCurrentGTTrackId());
        }

        return self;
    }

    public static String toQueryString(Map<String, Object> querys) {
        StringBuilder sb = new StringBuilder();

        for (var query : querys.entrySet()) {
            if (query.getKey() == null) continue;
            if (query.getValue() == null) continue;

            if (sb.length() > 0) sb.append("&");
            sb.append(query.getKey() + "=");
            sb.append(URLUtil.encodeAll(query.getValue().toString(), StandardCharsets.UTF_8));
        }

        return sb.toString();
    }

    public String getUrl() {
        return url;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpBuilder url(String url) {
        this.url = url;
        return this;
    }

    public HttpBuilder timeOut(int milliseconds) {
        this.timeOut = milliseconds;
        return this;
    }

    public HttpBuilder defToken(String client, String loginname) {
        this.defUserClient = client;
        this.defUserName = loginname;
        return this;
    }

    public HttpBuilder setTokenHeaders(String clientCode, String clientToken) {
        var client = MyApp.Of(clientCode).ToStr();
        if (StrUtil.isBlank(client)) client = MyUser.getCurrentUserClient();
        if (StrUtil.isBlank(client)) client = this.defUserClient;

        var uname = MyApp.Of(clientToken).ToStr();
        if (StrUtil.isBlank(uname)) uname = MyUser.getCurrentUserName();
        if (StrUtil.isBlank(uname)) uname = this.defUserName;
        this.headers.put(MyConst.HEADERKEY_TOKEN, MyApp.Server().TokenServer().EncryptToken(client, uname));

        return this;
    }

    public HttpBuilder setTokenHeaders() {
        return this.setTokenHeaders(null, null);
    }

    public HttpBuilder header(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public HttpBuilder headers(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public HttpGetBuilder buildGet() {
        return new HttpGetBuilder(this);
    }

    public HttpPostBuilder buildPost() {
        return new HttpPostBuilder(this);
    }

    public HttpPostFormBuilder buildPostForm() {
        return new HttpPostFormBuilder(this);
    }

    public HttpPostObjectBuilder buildPostObject() {
        return new HttpPostObjectBuilder(this);
    }

    public HttpPostFileBuilder buildPostBytes() {
        return new HttpPostFileBuilder(this);
    }
}
