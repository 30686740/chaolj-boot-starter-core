package com.chaolj.core.commonUtils.myBuilder;

import com.chaolj.core.MyConst;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class OfLocalDateTimeBuilder {
    private LocalDateTime data;

    public OfLocalDateTimeBuilder(LocalDateTime val){
        this.data = val;
    }

    public OfLocalDateTimeBuilder OfDef(LocalDateTime defVal) {
        if (this.data == null) this.data = defVal;
        return this;
    }

    public OfLocalDateTimeBuilder OfDef() {
        return this.OfDef(LocalDateTime.of(9999, 12, 31, 0, 0, 0));
    }

    // region ConvertTo

    public LocalDateTime Value(){
        return this.data;
    }

    public Long ToEpochSecond() {
        if (this.data == null) return null;
        return this.data.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    public Date ToDate() {
        if (this.data == null) return null;
        return Date.from(this.data.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Timestamp ToTimestamp() {
        if (this.data == null) return null;
        return Timestamp.valueOf(this.data);
    }

    public String ToFormat(DateTimeFormatter formatter){
        if (this.data == null) return "";

        if (formatter == DateTimeFormatter.ISO_OFFSET_DATE || formatter == DateTimeFormatter.ISO_OFFSET_TIME || formatter == DateTimeFormatter.ISO_OFFSET_DATE_TIME) {
            return this.data.atZone(ZoneId.systemDefault()).format(formatter);
        }
        else {
            return this.data.format(formatter);
        }
    }

    public String ToFormat(String format){
        return this.data == null ? "" : this.data.format(DateTimeFormatter.ofPattern(format));
    }

    public String ToFormat(){
        return this.ToFormat(MyConst.Pattern_yyyyMMddHHmmss);
    }

    // endregion
}
