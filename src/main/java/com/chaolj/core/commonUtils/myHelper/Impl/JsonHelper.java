package com.chaolj.core.commonUtils.myHelper.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.chaolj.core.bootUtils.bootObject.DateTimeFormatSerializer;
import com.chaolj.core.bootUtils.bootObject.ISODateTimeFormatSerializer;
import com.chaolj.core.bootUtils.bootObject.NumberFormatSerializer;
import com.chaolj.core.commonUtils.myHelper.MyHelperProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class JsonHelper {
    private ApplicationContext applicationContext;
    private MyHelperProperties properties;

    public JsonHelper(ApplicationContext applicationContext, MyHelperProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    public String toJSONString(Object object, SerializeConfig config, SerializerFeature... features) {
        return JSON.toJSONString(object, config, (SerializeFilter)null, features);
    }

    public String toJSONString(Object object, SerializeConfig config) {
        return JSON.toJSONString(object, config, SerializerFeature.WriteMapNullValue);
    }

    public String toJSONString(Object object, SerializerFeature... features) {
        var mapping = new SerializeConfig();
        mapping.put(java.util.Date.class, new DateTimeFormatSerializer());
        mapping.put(java.sql.Date.class, new DateTimeFormatSerializer());
        mapping.put(LocalDateTime.class, new DateTimeFormatSerializer());
        mapping.put(LocalDate.class, new DateTimeFormatSerializer());
        mapping.put(Timestamp.class, new DateTimeFormatSerializer());
        mapping.put(Float.class, new NumberFormatSerializer());
        mapping.put(Double.class, new NumberFormatSerializer());

        return JSON.toJSONString(object, mapping, features);
    }

    public String toJSONString(Object object) {
        var mapping = new SerializeConfig();
        mapping.put(java.util.Date.class, new DateTimeFormatSerializer());
        mapping.put(java.sql.Date.class, new DateTimeFormatSerializer());
        mapping.put(LocalDateTime.class, new DateTimeFormatSerializer());
        mapping.put(LocalDate.class, new DateTimeFormatSerializer());
        mapping.put(Timestamp.class, new DateTimeFormatSerializer());
        mapping.put(Float.class, new NumberFormatSerializer());
        mapping.put(Double.class, new NumberFormatSerializer());

        return JSON.toJSONString(object, mapping, SerializerFeature.WriteMapNullValue);
    }

    public String toJSONStringISO(Object object) {
        var mapping = new SerializeConfig();
        mapping.put(java.util.Date.class, new ISODateTimeFormatSerializer());
        mapping.put(java.sql.Date.class, new ISODateTimeFormatSerializer());
        mapping.put(LocalDateTime.class, new ISODateTimeFormatSerializer());
        mapping.put(LocalDate.class, new ISODateTimeFormatSerializer());
        mapping.put(Timestamp.class, new ISODateTimeFormatSerializer());
        mapping.put(Float.class, new NumberFormatSerializer());
        mapping.put(Double.class, new NumberFormatSerializer());

        return JSON.toJSONString(object, mapping, SerializerFeature.WriteMapNullValue);
    }

    public JSONObject parseObject(String text) {
        return JSON.parseObject(text);
    }

    public JSONObject parseObject(String text, Feature... features) {
        return JSON.parseObject(text, features);
    }

    public <T> T parseObject(String text, TypeReference<T> type) {
        return JSON.parseObject(text, type);
    }

    public <T> T parseObject(String text, TypeReference<T> type, Feature... features) {
        return JSON.parseObject(text, type, features);
    }

    public <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public <T> T parseObject(String text, Class<T> clazz, Feature... features) {
        return JSON.parseObject(text, clazz, features);
    }

    public <T> T parseObject(String text, Class<T> clazz, ParserConfig parserConfig) {
        return JSON.parseObject(text, clazz, parserConfig);
    }

    public <T> T parseObject(String text, Type type) {
        return JSON.parseObject(text, type);
    }

    public <T> T parseObject(String text, Type type, Feature... features) {
        return JSON.parseObject(text, type, features);
    }

    public JSONArray parseArray(String text) {
        return JSON.parseArray(text);
    }

    public JSONArray parseArray(String text, ParserConfig parserConfig) {
        return JSON.parseArray(text, parserConfig);
    }

    public <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    public <T> List<T> parseArray(String text, Class<T> clazz, ParserConfig config) {
        return JSON.parseArray(text, clazz, config);
    }
}
