package com.chaolj.core.commonUtils.myBuilder;

import cn.hutool.core.util.ArrayUtil;

public class OfStringArrayBuilder {
    private String[] data;

    public OfStringArrayBuilder(String[] array){
        this.data = array != null ? array : new String[0];
    }

    // region ConvertTo

    public String[] Value(){
        return this.data;
    }

    public String ToJoin(String speater){
        if (this.data.length <= 0) return "";
        return ArrayUtil.join(this.data, speater);
    }

    public String ToJoin(){
        return this.ToJoin(",");
    }

    // endregion
}
