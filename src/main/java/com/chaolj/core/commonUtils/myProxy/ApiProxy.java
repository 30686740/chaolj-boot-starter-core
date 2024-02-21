package com.chaolj.core.commonUtils.myProxy;

import cn.hutool.core.io.resource.BytesResource;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import com.chaolj.core.MyApp;
import com.chaolj.core.MyConst;
import com.chaolj.core.MyUser;
import com.chaolj.core.commonUtils.myBuilder.HttpBuilder;
import com.chaolj.core.commonUtils.myBuilder.HttpRespBuilder;
import com.chaolj.core.commonUtils.myBuilder.IHttpMethodBuilder;

public class ApiProxy<T> implements InvocationHandler {
    private Class<T> interfaces;

    ApiProxy(Class<T> interfaces) {
        this.interfaces = interfaces;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass().equals(interfaces)) {
            if (method.isDefault()) {
                final float version = Float.parseFloat(System.getProperty("java.class.version"));
                if (version <= 52) {
                    final Constructor<Lookup> constructor = Lookup.class.getDeclaredConstructor(Class.class);
                    constructor.setAccessible(true);

                    final Class<?> clazz = method.getDeclaringClass();
                    return constructor.newInstance(clazz)
                            .in(clazz)
                            .unreflectSpecial(method, clazz)
                            .bindTo(proxy)
                            .invokeWithArguments(args);
                }
                else {
                    return MethodHandles.lookup()
                            .findSpecial(
                                    method.getDeclaringClass(),
                                    method.getName(),
                                    MethodType.methodType(method.getReturnType(), method.getParameterTypes()),
                                    method.getDeclaringClass()
                            ).bindTo(proxy)
                            .invokeWithArguments(args);
                }
            }
            return this.doWork(proxy, method, args);
        } else {
            return method.invoke(new DefaultApiProxy(), args);
        }
    }

    private Object doWork(Object proxy, Method method, Object[] args) {
        var baseUrl = this.getBaseUrl(proxy, method, args);
        var requestObjects = this.getRequestObjects(proxy, method, args);
        var paramObjects = this.getParamObjects(proxy, method, args);
        var returnType = method.getReturnType();

        var requestUrl = baseUrl + requestObjects.get("requestUrl");
        if (StrUtil.isBlank(requestUrl)) throw new RuntimeException("@RequestMapping.Value 不能为空！");

        var httpBuilder = MyApp.Http().url(requestUrl);
        this.doHeader(httpBuilder, requestObjects, paramObjects);
        this.doPathVariable(httpBuilder, paramObjects);

        var requestMethod = requestObjects.get("requestMethod");
        if (StrUtil.equalsIgnoreCase(requestMethod, "POST")) {
            return this.doPost(httpBuilder, paramObjects, returnType);
        }
        else {
            return this.doGet(httpBuilder, paramObjects, returnType);
        }
    }

    private String getBaseUrl(Object proxy, Method method, Object[] args) {
        var baseUrl = "";

        var clazz = method.getDeclaringClass();
        var clazzRequestMapping = clazz.getAnnotation(RequestMapping.class);
        if (clazzRequestMapping != null) {
            var values = clazzRequestMapping.value();
            if (values != null && values.length > 0 && StrUtil.isNotBlank(values[0])) {
                baseUrl = values[0];

                if (baseUrl.startsWith("${") && baseUrl.endsWith("}")) {
                    baseUrl = baseUrl.substring(2);
                    baseUrl = baseUrl.substring(0, baseUrl.length()-1);
                    baseUrl = MyApp.Helper().ConfigHelper().getProperty(baseUrl);
                }
            }
        }

        return baseUrl;
    }

    private Map<String, String> getRequestObjects(Object proxy, Method method, Object[] args) {
        var map = new HashMap<String, String>();

        var requestUrl = "";
        var requestMethod = "GET";
        var requestContentType = "";
        var requestUserToken = "";

        var methodRequestMapping = method.getAnnotation(RequestMapping.class);
        if (methodRequestMapping != null) {
            var values = methodRequestMapping.value();
            if (values != null && values.length > 0 && StrUtil.isNotBlank(values[0])) {
                requestUrl = values[0];

                if (requestUrl.startsWith("${") && requestUrl.endsWith("}")) {
                    requestUrl = requestUrl.substring(2);
                    requestUrl = requestUrl.substring(0, requestUrl.length()-1);
                    requestUrl = MyApp.Helper().ConfigHelper().getProperty(requestUrl);
                }
            }

            var produces = methodRequestMapping.produces();
            if (produces != null && produces.length > 0 && StrUtil.isNotBlank(produces[0])) {
                requestContentType = produces[0];
            }

            var methods = methodRequestMapping.method();
            if (methods != null && methods.length > 0 && StrUtil.isNotBlank(methods[0].name())) {
                requestMethod = methods[0].name();
            }

            var headers = methodRequestMapping.headers();
            if (headers != null && headers.length > 0) {
                for (var header : headers) {
                    if (header.startsWith("SSOUserToken")) {
                        if (header.contains("=")) {
                            requestUserToken = StrUtil.subAfter(header, "=", false);
                            if (requestUserToken.contains("@")) {
                                var userName = StrUtil.subBefore(requestUserToken, "@", false);

                                var userContent = StrUtil.subAfter(requestUserToken, "@", false);
                                if (userContent.contains("@"))
                                {
                                    var userClient = StrUtil.subBefore(userContent, "@", false);
                                    var serverName = StrUtil.subAfter(userContent, "@", false);
                                    requestUserToken = MyApp.Server().TokenServer(serverName, false).EncryptToken(userClient, userName, 1);
                                }
                                else {
                                    requestUserToken = MyApp.Server().TokenServer().EncryptToken(userContent, userName, 1);
                                }
                            }
                            else {
                                var userClient = MyUser.getCurrentUserClient();
                                requestUserToken = MyApp.Server().TokenServer().EncryptToken(userClient, requestUserToken, 1);
                            }
                        }
                        else {
                            requestUserToken = MyUser.getCurrentUserToken();
                        }
                        break;
                    }
                }
            }
        }

        map.put("requestUrl", requestUrl);
        map.put("requestMethod", requestMethod);
        map.put("requestContentType", requestContentType);
        map.put("requestUserToken", requestUserToken);

        return map;
    }

    private Map<String, Object> getParamObjects(Object proxy, Method method, Object[] args) {
        var map = new HashMap<String, Object>();

        if (method.getParameters().length > 0) {
            for (var i = 0; i < method.getParameters().length; i++) {
                var p = method.getParameters()[i];
                var arg = args[i];

                var requestHeader = p.getAnnotation(RequestHeader.class);
                if (requestHeader != null) {
                    map.put("RequestHeader@" + requestHeader.value(), arg);
                    continue;
                }

                var pathVariableAnnotation = p.getAnnotation(PathVariable.class);
                if (pathVariableAnnotation != null) {
                    map.put("PathVariable@" + pathVariableAnnotation.value(), arg);
                    continue;
                }

                var requestParamAnnotation = p.getAnnotation(RequestParam.class);
                if (requestParamAnnotation != null) {
                    map.put("RequestParam@" + requestParamAnnotation.value(), arg);
                    continue;
                }

                var requestBodyAnnotation = p.getAnnotation(RequestBody.class);
                if (requestBodyAnnotation != null) {
                    map.put("RequestBody", arg);
                    continue;
                }

                map.put("RequestParam@" + p.getName(), arg);
            }
        }

        return map;
    }

    private void doHeader(HttpBuilder httpBuilder, Map<String, String> requestObjects, Map<String, Object> paramObjects) {
        var requestContentType = requestObjects.get("requestContentType");
        if (StrUtil.isNotBlank(requestContentType)) {
            httpBuilder.header("Content-Type", requestContentType);
        }

        var requestUserToken = requestObjects.get("requestUserToken");
        if (StrUtil.isNotBlank(requestUserToken)) {
            httpBuilder.header(MyConst.HEADERKEY_TOKEN, requestUserToken);
        }

        paramObjects.forEach((k, v) -> {
            if (k.startsWith("RequestHeader@")) {
                var name = k.replace("RequestHeader@", "");
                var value = v != null ? v.toString() : "";
                httpBuilder.header(name, value);
            }
        });
    }

    private void doPathVariable(HttpBuilder httpBuilder, Map<String, Object> paramObjects) {
        String[] paths = { httpBuilder.getUrl() };

        paramObjects.forEach((k, v) -> {
            if (k.startsWith("PathVariable@")) {
                var name = k.replace("PathVariable@", "");
                var value = v != null ? v.toString() : "";
                paths[0] = paths[0].replace("{" + name + "}", value);
            }
        });

        httpBuilder.url(paths[0]);
    }

    private Object doPost(HttpBuilder httpBuilder, Map<String, Object> paramObjects, Class<?> returnType) {
        if (paramObjects.containsKey("RequestBody")) {
            return this.doPostJson(httpBuilder, paramObjects, returnType);
        }
        else {
            return this.doPostForm(httpBuilder, paramObjects, returnType);
        }
    }

    private Object doPostJson(HttpBuilder httpBuilder, Map<String, Object> paramObjects, Class<?> returnType) {
        var builder = httpBuilder.buildPostObject();
        builder.body(paramObjects.get("RequestBody"));

        paramObjects.forEach((k, v) -> {
            if (k.startsWith("RequestParam@")) {
                var name = k.replace("RequestParam@", "");
                builder.query(name, v);
            }
        });

        return this.toExecute(builder, returnType);
    }

    private Object doPostForm(HttpBuilder httpBuilder, Map<String, Object> paramObjects, Class<?> returnType) {
        var builder = httpBuilder.buildPostBytes();
        paramObjects.forEach((k, v) -> {
            if (k.startsWith("RequestParam@")) {
                var name = k.replace("RequestParam@", "");

                if (v != null && StrUtil.equalsIgnoreCase(v.getClass().getName(), byte[].class.getName())) {
                    builder.file((byte[]) v, name, name);
                }
                else if (v != null && StrUtil.equalsIgnoreCase(v.getClass().getName(), BytesResource.class.getName())) {
                    var r = (BytesResource)v;
                    builder.file(r.readBytes(), r.getName(), name);
                }
                else {
                    builder.form(name, v);
                }
            }
        });

        return this.toExecute(builder, returnType);
    }

    private Object doGet(HttpBuilder httpBuilder, Map<String, Object> paramObjects, Class<?> returnType) {
        var builder = httpBuilder.buildGet();
        paramObjects.forEach((k, v) -> {
            if (k.startsWith("RequestParam@")) {
                var name = k.replace("RequestParam@", "");
                builder.query(name, v);
            }
        });

        return this.toExecute(builder, returnType);
    }

    private Object toExecute(IHttpMethodBuilder builder, Class<?> returnType) {
        var returnTypeName = returnType.getName();
        if (StrUtil.equalsIgnoreCase(returnTypeName, HttpRespBuilder.class.getName())) {
            return builder.execute();
        }
        else if (StrUtil.equalsIgnoreCase(returnTypeName, HttpResponse.class.getName())) {
            return builder.execute().getHttpResponse();
        }
        else if (StrUtil.equalsIgnoreCase(returnTypeName, String.class.getName())) {
            return builder.execute().getBodyString();
        }
        else if (StrUtil.equalsIgnoreCase(returnTypeName, JSONObject.class.getName())) {
            return builder.execute().toJsonObject();
        }
        else if (StrUtil.equalsIgnoreCase(returnTypeName, JSONArray.class.getName())) {
            return builder.execute().toJsonArray();
        }
        else if (StrUtil.equalsIgnoreCase(returnTypeName, byte[].class.getName())) {
            return builder.execute().getBodyBytes();
        }
        else {
            return builder.execute().toJavaObject(returnType);
        }
    }
}
