package com.chaolj.core.commonUtils.myBuilder;

import com.chaolj.core.MyApp;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class OfMapBuilder {
    private Map<String, Object> data;

    public OfMapBuilder(Map<String, Object> val){
        this.data = val;
    }

    public void setValue(String key, Object value){
        if (this.data == null) return;
        this.data.put(key, value);
    }

    public Object getValue(String key){
        if (this.data == null) return null;
        if (this.data.isEmpty()) return null;
        if (!this.data.containsKey(key)) return null;

        return this.data.get(key);
    }

    public String getString(String key, String defValue) {
        var value = this.getValue(key);
        return value == null ? (defValue == null ? "" : defValue) : value.toString();
    }

    public String getString(String key) {
        return this.getString(key, "");
    }

    public Integer getInteger(String key, Integer defValue) {
        var s = this.getString(key);
        return MyApp.Of(s).ToInteger(defValue);
    }

    public Integer getInteger(String key) {
        var s = this.getString(key);
        return MyApp.Of(s).ToInteger();
    }

    public Long getLong(String key, Long defValue) {
        var s = this.getString(key);
        return MyApp.Of(s).ToLong(defValue);
    }

    public Long getLong(String key) {
        var s = this.getString(key);
        return MyApp.Of(s).ToLong();
    }

    public Double getDouble(String key, Double defValue) {
        var s = this.getString(key);
        return MyApp.Of(s).ToDouble(defValue);
    }

    public Double getDouble(String key) {
        var s = this.getString(key);
        return MyApp.Of(s).ToDouble();
    }

    public Float getFloat(String key, float defValue) {
        var s = this.getString(key);
        return MyApp.Of(s).ToFloat(defValue);
    }

    public Float getFloat(String key) {
        var s = this.getString(key);
        return MyApp.Of(s).ToFloat();
    }

    public BigDecimal getBigDecimal(String key, BigDecimal defValue) {
        var s = this.getString(key);
        return MyApp.Of(s).ToBigDecimal(defValue);
    }

    public BigDecimal getBigDecimal(String key) {
        var s = this.getString(key);
        return MyApp.Of(s).ToBigDecimal();
    }

    public LocalDate getLocalDate(String key, LocalDate defValue) {
        var v = this.getValue(key);
        if (v == null) return defValue;

        if (v instanceof Date) {
            return MyApp.Of((Date)v).ToLocalDateTime().toLocalDate();
        }

        if (v instanceof Timestamp) {
            return ((Timestamp)v).toLocalDateTime().toLocalDate();
        }

        if (v instanceof LocalDateTime) {
            return ((LocalDateTime)v).toLocalDate();
        }

        if (v instanceof LocalDate) {
            return (LocalDate)v;
        }

        if (v instanceof String) {
            return MyApp.Of(v.toString()).ToLocalDate(defValue);
        }

        if (v instanceof Long) {
            var localDateTime = Instant.ofEpochMilli((Long)v).atZone(ZoneId.systemDefault()).toLocalDateTime();
            return localDateTime.toLocalDate();
        }

        return defValue;
    }

    public LocalDate getLocalDate(String key) {
        return this.getLocalDate(key, LocalDate.of(9999, 12, 31));
    }

    public LocalDateTime getLocalDateTime(String key, LocalDateTime defValue) {
        var v = this.getValue(key);
        if (v == null) return defValue;

        if (v instanceof Date) {
            return MyApp.Of((Date)v).ToLocalDateTime();
        }

        if (v instanceof Timestamp) {
            return ((Timestamp)v).toLocalDateTime();
        }

        if (v instanceof LocalDateTime) {
            return (LocalDateTime)v;
        }

        if (v instanceof LocalDate) {
            return ((LocalDate)v).atTime(0,0,0);
        }

        if (v instanceof String) {
            return MyApp.Of(v.toString()).ToLocalDateTime(defValue);
        }

        if (v instanceof Long) {
            return Instant.ofEpochMilli((Long)v).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }

        return defValue;
    }

    public LocalDateTime getLocalDateTime(String key) {
        return this.getLocalDateTime(key, LocalDateTime.of(9999, 12, 31, 0, 0, 0));
    }

    public Boolean getBoolean(String key, Boolean defValue) {
        var s = this.getString(key);
        return MyApp.Of(s).ToBoolean(defValue);
    }

    public Boolean getBoolean(String key) {
        var s = this.getString(key);
        return MyApp.Of(s).ToBoolean();
    }

    public <T> T toJavaObject(Class<T> clazz) {
        if (this.data == null) return null;
        if (this.data.isEmpty()) return null;

        var json = MyApp.Helper().JsonHelper().toJSONString(this.data);
        return MyApp.Helper().JsonHelper().parseObject(json, clazz);
    }
}
