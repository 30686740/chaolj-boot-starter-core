package com.chaolj.core.commonUtils.myBuilder;

import com.chaolj.core.MyConst;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class OfTimestampBuilder {
    private Timestamp data;

    public OfTimestampBuilder(Timestamp val){
        this.data = val;
    }

    public OfTimestampBuilder OfDef(Timestamp defVal) {
        if (this.data == null) this.data = defVal;
        return this;
    }

    public OfTimestampBuilder OfDef() {
        return this.OfDef(Timestamp.valueOf("9999-12-31 00:00:00"));
    }

    // region ConvertTo

    public Timestamp Value(){
        return this.data;
    }

    public Long ToEpochSecond() {
        if (this.data == null) return null;
        return this.data.toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    public LocalDateTime ToLocalDateTime() {
        if (this.data == null) return null;
        return this.data.toLocalDateTime();
    }

    public String ToFormat(String pattern){
        return this.data == null ? "" : (new SimpleDateFormat(pattern)).format(this.data);
    }

    public String ToFormat(){
        return this.ToFormat(MyConst.Pattern_yyyyMMddHHmmss);
    }

    // endregion
}
