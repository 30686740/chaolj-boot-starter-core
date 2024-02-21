package com.chaolj.core.commonUtils.myBuilder;

import com.chaolj.core.MyConst;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class OfLocalDateBuilder {
    private LocalDate data;

    public OfLocalDateBuilder(LocalDate val){
        this.data = val;
    }

    public OfLocalDateBuilder OfDef(LocalDate defVal) {
        if (this.data == null) this.data = defVal;
        return this;
    }

    public OfLocalDateBuilder OfDef() {
        return this.OfDef(LocalDate.of(9999, 12, 31));
    }

    // region ConvertTo

    public LocalDate Value(){
        return this.data;
    }

    public Long ToEpochSecond() {
        if (this.data == null) return null;
        return this.data.atTime(0,0,0).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    public String ToFormat(DateTimeFormatter formatter){
        if (this.data == null) return "";

        if (formatter == DateTimeFormatter.ISO_OFFSET_DATE || formatter == DateTimeFormatter.ISO_OFFSET_TIME || formatter == DateTimeFormatter.ISO_OFFSET_DATE_TIME) {
            return this.data.atTime(0,0,0).atZone(ZoneId.systemDefault()).format(formatter);
        }
        else {
            return this.data.format(formatter);
        }
    }

    public String ToFormat(String format){
        if (this.data == null) return "";
        return this.data.format(DateTimeFormatter.ofPattern(format));
    }

    public String ToFormat(){
        return this.ToFormat(MyConst.Pattern_yyyyMMdd);
    }

    // endregion
}
