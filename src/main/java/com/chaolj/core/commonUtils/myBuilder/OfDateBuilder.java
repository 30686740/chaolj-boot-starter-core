package com.chaolj.core.commonUtils.myBuilder;

import com.chaolj.core.MyConst;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class OfDateBuilder {
    private Date data;

    public OfDateBuilder(Date val){
        this.data = val;
    }

    public OfDateBuilder OfDef(Date defVal) {
        if (this.data == null) this.data = defVal;
        return this;
    }

    public OfDateBuilder OfDef() {
        return this.OfDef(Date.from(LocalDateTime.of(9999,12,31,0,0,0).atZone(ZoneId.systemDefault()).toInstant()));
    }

    // region ConvertTo

    public Date Value(){
        return this.data;
    }

    public Long ToEpochSecond() {
        if (this.data == null) return null;
        return this.data.toInstant().getEpochSecond();
    }

    public LocalDateTime ToLocalDateTime() {
        if (this.data == null) return null;
        return this.data.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public String ToFormat(DateTimeFormatter formatter){
        if (this.data == null) return "";
        return this.data.toInstant().atZone(ZoneId.systemDefault()).format(formatter);
    }

    public String ToFormat(String pattern){
        return this.data == null ? "" :  (new SimpleDateFormat(pattern)).format(this.data);
    }

    public String ToFormat(){
        return this.ToFormat(MyConst.Pattern_yyyyMMddHHmmss);
    }

    // endregion
}
