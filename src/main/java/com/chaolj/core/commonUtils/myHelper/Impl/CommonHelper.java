package com.chaolj.core.commonUtils.myHelper.Impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.context.ApplicationContext;
import com.chaolj.core.commonUtils.myHelper.MyHelperProperties;

@Slf4j
public class CommonHelper {
    private ApplicationContext applicationContext;
    private MyHelperProperties properties;

    public CommonHelper(ApplicationContext applicationContext, MyHelperProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    public String QuoteFieldName(String fieldName, DbType dbType, String alias){
        if (StrUtil.isBlank(fieldName)) return "";

        var _fieldName = fieldName.trim();
        if (dbType == DbType.MYSQL) {
            _fieldName = _fieldName.replace("`", "");
            _fieldName = "`" + _fieldName + "`";
        }
        else if (dbType == DbType.ORACLE || dbType == DbType.ORACLE_12C) {
            _fieldName = _fieldName.replace("\"", "");
            _fieldName = "\"" + _fieldName + "\"";
        }
        else if (dbType == DbType.SQL_SERVER || dbType == DbType.SQL_SERVER2005) {
            _fieldName = _fieldName.replace("[", "");
            _fieldName = _fieldName.replace("]", "");
            _fieldName = "[" + _fieldName + "]";
        }

        if (!StrUtil.isBlank(alias)) {
            var _alias = alias.trim();
            _fieldName = _alias + "." + _fieldName;
        }

        return _fieldName;
    }

    public String QuoteFieldName(String fieldName, DbType dbType){
        return this.QuoteFieldName(fieldName, dbType, "t1");
    }

    public String QuoteFieldName(String fieldName){
        return this.QuoteFieldName(fieldName, DbType.MYSQL, "t1");
    }

    public String QuoteFieldValue(String sqlValue){
        if (sqlValue == null) return "null";
        return "'" + StrUtil.replace(sqlValue, "'", "''") + "'";
    }

    public String QuoteFieldValueLike(String sqlValue){
        if (sqlValue == null) return "null";
        return "'%" + StrUtil.replace(sqlValue, "'", "''") + "%'";
    }

    public String QuoteFieldValueStartsWith(String sqlValue){
        if (sqlValue == null) return "null";
        return "'" + StrUtil.replace(sqlValue, "'", "''") + "%'";
    }

    public String QuoteFieldValueEndsWith(String sqlValue){
        if (sqlValue == null) return "null";
        return "'%" + StrUtil.replace(sqlValue, "'", "''") + "'";
    }

    public LocalDateTime QuoteDateTime(LocalDateTime dateValue){
        var min = LocalDateTime.of(1900, 1, 1, 0, 0);
        var max = LocalDateTime.of(9999, 12, 31, 0, 0);

        if (dateValue == null) return null;
        if (dateValue.getYear() >= 9999) return max;
        if (dateValue.getYear() <= 1900) return min;
        return dateValue;
    }

    public LocalDate QuoteDateTime(LocalDate dateValue){
        var min = LocalDate.of(1900, 1, 1);
        var max = LocalDate.of(9999, 12, 31);

        if (dateValue == null) return null;
        if (dateValue.getYear() >= 9999) return max;
        if (dateValue.getYear() <= 1900) return min;
        return dateValue;
    }

    public Date QuoteDateTime(Date dateValue){
        var min = Date.valueOf("1900-01-01");
        var max = Date.valueOf("9999-12-31");

        if (dateValue == null) return null;
        if (dateValue.toLocalDate().getYear() >= 9999) return max;
        if (dateValue.toLocalDate().getYear() <= 1900) return min;
        return dateValue;
    }

    public Timestamp QuoteDateTime(Timestamp dateValue){
        var min = Timestamp.valueOf("1900-01-01 00:00:00");
        var max = Timestamp.valueOf("9999-12-31 00:00:00");

        if (dateValue == null) return null;
        if (dateValue.toLocalDateTime().getYear() >= 9999) return max;
        if (dateValue.toLocalDateTime().getYear() <= 1900) return min;
        return dateValue;
    }

    public String ToPinYin(String china) {
        var formart = new HanyuPinyinOutputFormat();
        formart.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        formart.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        formart.setVCharType(HanyuPinyinVCharType.WITH_V);

        var arrays = china.trim().toCharArray();
        var result = "";
        try {
            for (int i=0; i<arrays.length; i++) {
                char ti = arrays[i];

                //匹配是否是中文
                if(Character.toString(ti).matches("[\\u4e00-\\u9fa5]")){
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(ti,formart);
                    result += temp[0];
                }else{
                    result += ti;
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }

        return result;
    }

    public String ToPinYinFirst(String china) {
        var formart = new HanyuPinyinOutputFormat();
        formart.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        formart.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        formart.setVCharType(HanyuPinyinVCharType.WITH_V);

        var arrays = china.trim().toCharArray();
        var result = "";
        try {
            for (int i=0; i<arrays.length; i++) {
                char ti = arrays[i];

                //匹配是否是中文
                if(Character.toString(ti).matches("[\\u4e00-\\u9fa5]")){
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(ti,formart);
                    result += temp[0].substring(0,1);
                }else{
                    result += ti;
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }

        return result;
    }

    public String Format(String pattern, Object ... arguments) {
        if (StrUtil.isBlank(pattern)) return null;
        return MessageFormat.format(pattern, arguments);
    }
}
