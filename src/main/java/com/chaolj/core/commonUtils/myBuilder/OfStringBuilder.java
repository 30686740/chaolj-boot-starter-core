package com.chaolj.core.commonUtils.myBuilder;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OfStringBuilder {
    private String data;

    public OfStringBuilder(String val){
        this.data = val == null ? "" : val;
    }

    public OfStringBuilder Trim(){
        this.data = this.data.trim();
        return this;
    }

    public OfStringBuilder LowerCase(){
        this.data = this.data.toLowerCase();
        return this;
    }

    public OfStringBuilder UpperCase(){
        this.data = this.data.toUpperCase();
        return this;
    }

    public OfStringBuilder SubAfter(String speater){
        this.data = StrUtil.subAfter(this.data, speater, false);
        return this;
    }

    public OfStringBuilder SubLastAfter(String speater){
        this.data = StrUtil.subAfter(this.data, speater, true);
        return this;
    }

    public OfStringBuilder SubBefore(String speater){
        this.data = StrUtil.subBefore(this.data, speater, false);
        return this;
    }

    public OfStringBuilder SubLastBefore(String speater){
        this.data = StrUtil.subBefore(this.data, speater, true);
        return this;
    }

    public OfStringBuilder AppendPrefix(String prefix){
        this.data = StrUtil.addPrefixIfNot(this.data, prefix);
        return this;
    }

    public OfStringBuilder DeletePrefix(String prefix){
        this.data = StrUtil.removePrefix(this.data, prefix);
        return this;
    }

    public OfStringBuilder AppendSuffix(String suffix){
        this.data = StrUtil.addSuffixIfNot(this.data, suffix);
        return this;
    }

    public OfStringBuilder DeleteSuffix(String suffix){
        this.data = StrUtil.removeSuffix(this.data, suffix);
        return this;
    }

    public OfStringBuilder EncodeFilePath(String replace){
        this.data = StrUtil.replace(this.data, "/", replace);
        this.data = StrUtil.replace(this.data, "\\", replace);
        this.data = StrUtil.replace(this.data, ":", replace);
        this.data = StrUtil.replace(this.data, ">", replace);
        this.data = StrUtil.replace(this.data, "<", replace);
        this.data = StrUtil.replace(this.data, "*", replace);
        this.data = StrUtil.replace(this.data, "|", replace);
        this.data = StrUtil.replace(this.data, "?", replace);
        this.data = StrUtil.replace(this.data, "\"", replace);

        return this;
    }

    public OfStringBuilder EncodeFilePath(){
        return this.EncodeFilePath("!!");
    }

    public OfStringBuilder EncodeJson(){
        this.data = StrUtil.replace(this.data, "\"", "\\\"");
        this.data = StrUtil.replace(this.data, "\'", "\\\'");
        this.data = "\"" + this.data + "\"";
        return this;
    }

    public OfStringBuilder EncodeXML(){
        this.data = StrUtil.replace(this.data, "&", "&amp;");
        this.data = StrUtil.replace(this.data, "<", "&lt;");
        this.data = StrUtil.replace(this.data, ">", "&gt;");
        this.data = StrUtil.replace(this.data, "\"", "&quot;");
        this.data = StrUtil.replace(this.data, "\'", "&apos;");

        return this;
    }

    public OfStringBuilder EncodeSQL(){
        this.data = StrUtil.replace(this.data, "'", "''");
        return this;
    }

    public OfStringBuilder EncodeURL(Charset charset){
        this.data = URLUtil.encodeAll(this.data, charset);
        return this;
    }

    public OfStringBuilder EncodeURL(){
        return this.EncodeURL(StandardCharsets.UTF_8);
    }

    public OfStringBuilder DecodeURL(Charset charset){
        this.data = URLUtil.decode(this.data, charset);
        return this;
    }

    public OfStringBuilder DecodeURL(){
        return this.DecodeURL(StandardCharsets.UTF_8);
    }

    public OfStringBuilder EncodeBase64String(Charset charset){
        var bytes = this.data.getBytes(charset);
        this.data = Base64.encodeBase64String(bytes);
        return this;
    }

    public OfStringBuilder EncodeBase64String(){
        return this.EncodeBase64String(StandardCharsets.UTF_8);
    }

    public OfStringBuilder DecodeBase64String(Charset charset){
        var bytes = Base64.decodeBase64(this.data);
        this.data = new String(bytes, charset);
        return this;
    }

    public OfStringBuilder DecodeBase64String(){
        return this.DecodeBase64String(StandardCharsets.UTF_8);
    }

    public OfStringBuilder EncodeMD5(Charset charset){
        this.data = SecureUtil.md5().digestHex(this.data, StandardCharsets.UTF_8);
        return this;
    }

    public OfStringBuilder EncodeMD5(){
        return this.EncodeMD5(StandardCharsets.UTF_8);
    }

    // region ConverTo

    public String Value(){
        return this.data;
    }

    public String ToStr(String def){
        if (StrUtil.isBlank(this.data)) return def;
        return this.Trim().Value();
    }

    public String ToStr(){
        return this.Trim().Value();
    }

    public Integer ToInteger(Integer def){
        var _data = this.data.trim();
        if (StrUtil.isBlank(_data)) return def;

        var value = def;
        try {
            value = Integer.valueOf(_data);
        }
        catch (Exception ignored){
        }

        return value;
    }

    public Integer ToInteger(){
        return this.ToInteger(0);
    }

    public Long ToLong(Long def){
        var _data = this.data.trim();
        if (StrUtil.isBlank(_data)) return def;

        var value = def;
        try {
            value = Long.valueOf(_data);
        }
        catch (Exception ignored){
        }

        return value;
    }

    public Long ToLong(){
        return this.ToLong(0L);
    }

    public Double ToDouble(Double def){
        var _data = this.data.trim();
        if (StrUtil.isBlank(_data)) return def;

        var value = def;
        try {
            value = Double.valueOf(_data);
        }
        catch (Exception ignored){
        }

        return value;
    }

    public Double ToDouble(){
        return this.ToDouble(0D);
    }

    public Float ToFloat(Float def){
        var _data = this.data.trim();
        if (StrUtil.isBlank(_data)) return def;

        var value = def;
        try {
            value = Float.valueOf(_data);
        }
        catch (Exception ignored){
        }

        return value;
    }

    public Float ToFloat(){
        return this.ToFloat(0F);
    }

    public java.math.BigDecimal ToBigDecimal(java.math.BigDecimal def){
        var _data = this.data.trim();
        if (StrUtil.isBlank(_data)) return def;

        var value = def;
        try {
            value = new java.math.BigDecimal(_data);
        }
        catch (Exception ignored){
        }

        return value;
    }

    public java.math.BigDecimal ToBigDecimal(){
        return this.ToBigDecimal(java.math.BigDecimal.valueOf(0));
    }

    public Boolean ToBoolean(Boolean def){
        var _data = this.data.trim();
        if (StrUtil.isBlank(_data)) return def;

        if (_data.equalsIgnoreCase("true")) return true;
        if (_data.equalsIgnoreCase("1")) return true;
        if (_data.equalsIgnoreCase("false")) return false;
        if (_data.equalsIgnoreCase("0")) return false;

        return def;
    }

    public Boolean ToBoolean(){
        return this.ToBoolean(false);
    }

    public LocalDate ToLocalDate(LocalDate def){
        var _data = data.trim();
        if (StrUtil.isBlank(_data)) return def;

        try {
            return DateUtil.parse(_data).toLocalDateTime().toLocalDate();
        }
        catch (Exception exception) {
            return def;
        }
    }

    public LocalDate ToLocalDate(){
        return this.ToLocalDate(LocalDate.of(9999, 12, 31));
    }

    public LocalDateTime ToLocalDateTime(LocalDateTime def){
        var _data = data.trim();
        if (StrUtil.isBlank(_data)) return def;

        try {
            return DateUtil.parse(_data).toLocalDateTime();
        }
        catch (Exception exception) {
            return def;
        }
    }

    public LocalDateTime ToLocalDateTime(){
        return this.ToLocalDateTime(LocalDateTime.of(9999, 12, 31, 0, 0, 0));
    }

    public String[] ToArray(String speater){
        if (StrUtil.isBlank(this.data)) return new String[0];
        if (speater == null) return new String[0];
        if (speater.length() == 0) return new String[0];

        return StrUtil.splitToArray(this.data, speater.charAt(0));
    }

    public List<String> ToList(String speater){
        if (StrUtil.isBlank(this.data)) return new ArrayList(0);
        if (speater == null) return new ArrayList(0);
        if (speater.length() == 0) return new ArrayList(0);

        return StrUtil.split(this.data, speater.charAt(0));
    }

    public List<Integer> ToListInt(String speater){
        var items = new ArrayList<Integer>();
        for (String s : this.ToList(speater)) {
            if (StrUtil.isBlank(s)) continue;
            try {
                items.add(Integer.valueOf(s.trim()));
            }
            catch (Exception ex) {
                throw new RuntimeException(s + "，无法转换 -> Integer");
            }
        }
        return items;
    }

    public List<Long> ToListLong(String speater){
        var items = new ArrayList<Long>();
        for (String s : this.ToList(speater)) {
            if (StrUtil.isBlank(s)) continue;
            try {
                items.add(Long.valueOf(s.trim()));
            }
            catch (Exception ex) {
                throw new RuntimeException(s + "，无法转换 -> Long");
            }
        }
        return items;
    }

    public List<Double> ToListDouble(String speater){
        var items = new ArrayList<Double>();
        for (String s : this.ToList(speater)) {
            if (StrUtil.isBlank(s)) continue;
            try {
                items.add(Double.valueOf(s.trim()));
            }
            catch (Exception ex) {
                throw new RuntimeException(s + "，无法转换 -> Double");
            }
        }
        return items;
    }

    public List<Float> ToListFloat(String speater){
        var items = new ArrayList<Float>();
        for (String s : this.ToList(speater)) {
            if (StrUtil.isBlank(s)) continue;
            try {
                items.add(Float.valueOf(s.trim()));
            }
            catch (Exception ex) {
                throw new RuntimeException(s + "，无法转换 -> Float");
            }
        }
        return items;
    }

    public List<java.math.BigDecimal> ToListBigDecimal(String speater){
        var items = new ArrayList<java.math.BigDecimal>();
        for (String s : this.ToList(speater)) {
            if (StrUtil.isBlank(s)) continue;
            try {
                items.add(new java.math.BigDecimal(s.trim()));
            }
            catch (Exception ex) {
                throw new RuntimeException(s + "，无法转换 -> BigDecimal");
            }
        }
        return items;
    }

    public List<LocalDate> ToListDate(String speater){
        var items = new ArrayList<LocalDate>();
        for (String s : this.ToList(speater)) {
            if (StrUtil.isBlank(s)) continue;
            try {
                items.add(LocalDate.parse(s.trim()));
            }
            catch (Exception ex) {
                throw new RuntimeException(s + "，无法转换 -> LocalDate");
            }
        }
        return items;
    }

    public List<LocalDateTime> ToListDateTime(String speater, String format){
        var items = new ArrayList<LocalDateTime>();

        for (String s : this.ToList(speater)) {
            if (StrUtil.isBlank(s)) continue;

            try {
                var formatter = s.contains("T") ? DateTimeFormatter.ISO_LOCAL_DATE_TIME : DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                if (!StrUtil.isBlank(format)) formatter = DateTimeFormatter.ofPattern(format);
                items.add(LocalDateTime.parse(s.trim(), formatter));
            }
            catch (Exception ex) {
                throw new RuntimeException(s + "，无法转换 -> LocalDateTime");
            }
        }
        return items;
    }

    public List<LocalDateTime> ToListDateTime(String speater){
        return this.ToListDateTime(speater, "");
    }

    // endregion
}
