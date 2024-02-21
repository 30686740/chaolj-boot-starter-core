package com.chaolj.core.commonUtils.myDto;

import java.io.Serializable;

public enum DataStatusDto implements Serializable {
    _0_Apply("申请中", 0),
    _1_Approved("已批准", 1),
    _f1_Canceled("已撤销", -1);

    private String name;
    private Integer value;

    DataStatusDto(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static DataStatusDto ValueOf(Integer value){
        DataStatusDto result = null;
        for (var item : DataStatusDto.values()){
            if (item.value.equals(value)) {
                result = item;
                break;
            }
        }

        if (result == null) throw new RuntimeException("值（" + value + "），不在枚举（DataStatusDto）范围内！");

        return result;
    }

    public static DataStatusDto ValueOf(Long longvalue){
        var value = longvalue.intValue();
        return ValueOf(value);
    }
}
