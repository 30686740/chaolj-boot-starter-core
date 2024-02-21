package com.chaolj.core.commonUtils.myBuilder;

import com.chaolj.core.MyApp;
import com.chaolj.core.commonUtils.myDelegate.ActionDelegate2;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class OfMapListBuilder {
    private List<Map<String, Object>> data;

    public OfMapListBuilder(List<Map<String, Object>> val){
        this.data = val;
    }

    public void foreach(ActionDelegate2<Integer, Map<String, Object>> action) {
        if (this.data == null) return;
        if (this.data.isEmpty()) return;

        for (int i = 0; i < this.data.size(); i++) {
            action.invoke(i, this.data.get(i));
        }
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    public int size() {
        return this.data.size();
    }

    public Map<String, Object> get(int index) {
        return this.data.get(index);
    }

    public Object getValue(int index, String key) {
        var e = this.data.get(index);
        if (e == null) return null;
        if (e.isEmpty()) return null;
        if (!e.containsKey(key)) return null;

        return e.get(key);
    }

    public void setValue(int index, String key, Object value){
        var e = this.data.get(index);
        if (e == null) return;

        e.put(key, value);
    }

    public String getString(int index, String key, String defValue) {
        var value = this.getValue(index, key);
        return value == null ? (defValue == null ? "" : defValue) : value.toString();
    }

    public String getString(int index, String key) {
        return this.getString(index, key, "");
    }

    public Integer getInteger(int index, String key, Integer defValue) {
        var s = this.getString(index, key);
        return MyApp.Of(s).ToInteger(defValue);
    }

    public Integer getInteger(int index, String key) {
        var s = this.getString(index, key);
        return MyApp.Of(s).ToInteger();
    }

    public Long getLong(int index, String key, Long defValue) {
        var s = this.getString(index, key);
        return MyApp.Of(s).ToLong(defValue);
    }

    public Long getLong(int index, String key) {
        var s = this.getString(index, key);
        return MyApp.Of(s).ToLong();
    }

    public Double getDouble(int index, String key, Double defValue) {
        var s = this.getString(index, key);
        return MyApp.Of(s).ToDouble(defValue);
    }

    public Double getDouble(int index, String key) {
        var s = this.getString(index, key);
        return MyApp.Of(s).ToDouble();
    }

    public Float getFloat(int index, String key, float defValue) {
        var s = this.getString(index, key);
        return MyApp.Of(s).ToFloat(defValue);
    }

    public Float getFloat(int index, String key) {
        var s = this.getString(index, key);
        return MyApp.Of(s).ToFloat();
    }

    public BigDecimal getBigDecimal(int index, String key, BigDecimal defValue) {
        var s = this.getString(index, key);
        return MyApp.Of(s).ToBigDecimal(defValue);
    }

    public BigDecimal getBigDecimal(int index, String key) {
        var s = this.getString(index, key);
        return MyApp.Of(s).ToBigDecimal();
    }

    public LocalDate getLocalDate(int index, String key, LocalDate defValue) {
        var v = this.getValue(index, key);
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

    public LocalDate getLocalDate(int index, String key) {
        return this.getLocalDate(index, key, LocalDate.of(9999, 12, 31));
    }

    public LocalDateTime getLocalDateTime(int index, String key, LocalDateTime defValue) {
        var v = this.getValue(index, key);
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

    public LocalDateTime getLocalDateTime(int index, String key) {
        return this.getLocalDateTime(index, key, LocalDateTime.of(9999, 12, 31, 0, 0, 0));
    }

    public Boolean getBoolean(int index, String key, Boolean defValue) {
        var s = this.getString(index, key);
        return MyApp.Of(s).ToBoolean(defValue);
    }

    public Boolean getBoolean(int index, String key) {
        var s = this.getString(index, key);
        return MyApp.Of(s).ToBoolean();
    }

    public <T> List<T> toJavaObject(Class<T> clazz) {
        if (this.data == null) return null;
        if (this.data.isEmpty()) return null;

        var json = MyApp.Helper().JsonHelper().toJSONString(this.data);
        return MyApp.Helper().JsonHelper().parseArray(json, clazz);
    }
}
