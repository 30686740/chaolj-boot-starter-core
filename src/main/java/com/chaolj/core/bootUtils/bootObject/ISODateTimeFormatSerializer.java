package com.chaolj.core.bootUtils.bootObject;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ISODateTimeFormatSerializer implements ObjectSerializer {
    private final DateTimeFormatter pattern;

    public ISODateTimeFormatSerializer() {
        this.pattern = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) {
            serializer.out.writeNull();
        } else {
            if (object instanceof Date) {
                Date date = (Date)object;
                String text = date.toInstant().atZone(ZoneId.systemDefault()).format(this.pattern);
                serializer.write(text);
            }
            else if (object instanceof java.sql.Date) {
                java.sql.Date date = (java.sql.Date)object;
                String text = date.toInstant().atZone(ZoneId.systemDefault()).format(this.pattern);
                serializer.write(text);
            }
            else if (object instanceof LocalDate) {
                LocalDate date = (LocalDate)object;
                String text = date.atTime(0,0,0).atZone(ZoneId.systemDefault()).format(this.pattern);
                serializer.write(text);
            }
            else if (object instanceof LocalDateTime) {
                LocalDateTime date = (LocalDateTime)object;
                String text = date.atZone(ZoneId.systemDefault()).format(this.pattern);
                serializer.write(text);
            }
            else if (object instanceof Timestamp) {
                Timestamp date = (Timestamp)object;
                String text = date.toLocalDateTime().atZone(ZoneId.systemDefault()).format(this.pattern);
                serializer.write(text);
            }
        }
    }
}
