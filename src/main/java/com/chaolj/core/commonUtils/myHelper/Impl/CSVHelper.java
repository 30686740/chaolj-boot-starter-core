package com.chaolj.core.commonUtils.myHelper.Impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.chaolj.core.commonUtils.myHelper.MyHelperProperties;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class CSVHelper {
    private ApplicationContext applicationContext;
    private MyHelperProperties properties;

    public CSVHelper(ApplicationContext applicationContext, MyHelperProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    public String FormatFirstUpperCase(String str) {
        if (StrUtil.isBlank(str)) return "";

        char[] chars = str.toCharArray();
        if(!Character.isUpperCase(chars[0])){
            return str.substring(0,1).toUpperCase()+str.substring(1);
        }
        return String.valueOf(chars);
    }

    public String FormatCSVCell(Object o, String typeName) {
        if (o == null) {
            return "";
        }
        else if (typeName.contains("LocalDateTime")) {
            var v = (LocalDateTime)o;
            return v.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        else if (typeName.contains("LocalDate")) {
            var v = (LocalDate)o;
            return v.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        else if (typeName.contains("Date")) {
            var v = (Date)o;
            return DateUtil.format(v, "yyyy-MM-dd HH:mm:ss");
        }
        else if (typeName.contains("Timestamp")) {
            var v = (Timestamp)o;
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(v);
        }
        else if (StrUtil.containsAnyIgnoreCase(typeName, "int", "Integer", "Long", "Double", "Float", "BigDecimal", "Short")) {
            return o.toString();
        }
        else
        {
            var v = o.toString()
                    .replace("\"", "\"\"")
                    .replace("\n", "")
                    .replace("\r", "");

            if (v.contains(",")) {
                return "\"" + v + "\"";
            }
            else if (StrUtil.isNumeric(v)) {
                return "\"'" + v + "\"";
            }
            else {
                return v;
            }
        }
    }

    public String FormatCSVCell(Object o, Class<?> clazz) {
        var typeName = clazz.toString();
        return this.FormatCSVCell(o, typeName);
    }

    public String FormatCSVCell(Object o, Type type) {
        var typeName = type.toString();
        return this.FormatCSVCell(o, typeName);
    }

    public String FormatCSVTitles(Class<?> clazz) {
        var fieldNames = new ArrayList<String>();

        var fields = clazz.getDeclaredFields();
        for(var field : fields) {
            var annotation = field.getAnnotation(ApiModelProperty.class);
            var displayName = annotation != null ? annotation.value() : field.getName();

            fieldNames.add(this.FormatCSVCell(displayName, String.class));
        }

        return ArrayUtil.join(fieldNames.toArray(), ",");
    }

    public <T> String FormatCSVRows(Iterable<T> records, Class<T> clazz) {
        var rows = new StringBuilder();

        var fields = clazz.getDeclaredFields();
        for (var record : records) {
            var fieldValues = new ArrayList<String>();
            for (var field : fields) {
                var fieldType = field.getGenericType();
                var fieldName = field.getName();
                var method = ReflectUtil.getMethod(clazz, "get"+this.FormatFirstUpperCase(fieldName));

                Object fieldValue = null;
                try {
                    fieldValue = method.invoke(record);
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                fieldValues.add(this.FormatCSVCell(fieldValue, fieldType));
            }

            rows.append(ArrayUtil.join(fieldValues.toArray(), ","));
            rows.append(System.lineSeparator());
        }

        return rows.toString();
    }

    public <T> String FormatCSV(Iterable<T> records, Class<T> clazz) {
        var heads = this.FormatCSVTitles(clazz);
        var rows = this.FormatCSVRows(records, clazz);

        return heads + System.lineSeparator() + rows;
    }
}
