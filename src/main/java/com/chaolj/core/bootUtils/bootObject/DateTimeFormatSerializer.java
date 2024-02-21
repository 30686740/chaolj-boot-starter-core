package com.chaolj.core.bootUtils.bootObject;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeFormatSerializer implements ObjectSerializer {
    private final String pattern;

    public DateTimeFormatSerializer(String pattern) {
        this.pattern = pattern;
    }

    public DateTimeFormatSerializer() {
        this.pattern = "yyyy-MM-dd HH:mm:ss";
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) {
            serializer.out.writeNull();
        } else {
            if (object instanceof Date) {
                Date date = (Date)object;
                SimpleDateFormat format = new SimpleDateFormat(this.pattern);
                String text = format.format(date);
                serializer.write(text);
            }
            else if (object instanceof java.sql.Date) {
                java.sql.Date date = (java.sql.Date)object;
                SimpleDateFormat format = new SimpleDateFormat(this.pattern);
                String text = format.format(date);
                serializer.write(text);
            }
            else if (object instanceof LocalDate) {
                LocalDate date = (LocalDate)object;
                String text = date.atTime(0,0,0).format(DateTimeFormatter.ofPattern(this.pattern));
                serializer.write(text);
            }
            else if (object instanceof LocalDateTime) {
                LocalDateTime date = (LocalDateTime)object;
                String text = date.format(DateTimeFormatter.ofPattern(this.pattern));
                serializer.write(text);
            }
            else if (object instanceof Timestamp) {
                Timestamp date = (Timestamp)object;
                String text = date.toLocalDateTime().format(DateTimeFormatter.ofPattern(this.pattern));
                serializer.write(text);
            }
        }
    }
}
