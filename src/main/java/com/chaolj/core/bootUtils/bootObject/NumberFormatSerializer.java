package com.chaolj.core.bootUtils.bootObject;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;

public class NumberFormatSerializer implements ObjectSerializer {
    private final String pattern;

    public NumberFormatSerializer(String pattern) {
        this.pattern = pattern;
    }

    public NumberFormatSerializer() {
        this.pattern = "#0.0#######";
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) {
            serializer.out.writeNull();
            return;
        }

        if (object instanceof Float) {
            Float date = (Float)object;
            var format = new DecimalFormat(this.pattern);
            String text = format.format(date);
            serializer.out.write(text);
            return;
        }

        if (object instanceof Double) {
            Double date = (Double)object;
            var format = new DecimalFormat(this.pattern);
            String text = format.format(date);
            serializer.out.write(text);
            return;
        }
    }
}
