package com.chaolj.core.commonUtils.myDto;

import lombok.Data;
import java.io.Serializable;

@Data
public class DataColumnDto  implements Serializable {
    private String ColumnName;
    private String ColumnLabel;
    private int ColumnType;
    private String ColumnTypeName;
    private String ColumnClassName;
    private int ColumnDisplaySize;
    private int Nullable;
    private boolean AutoIncrement;
    private int Precision;
    private int Scale;
}
