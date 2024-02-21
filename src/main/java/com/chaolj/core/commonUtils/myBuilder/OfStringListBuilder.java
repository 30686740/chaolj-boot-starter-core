package com.chaolj.core.commonUtils.myBuilder;

import cn.hutool.core.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

public class OfStringListBuilder {
    private List<String> data;

    public OfStringListBuilder(List<String> array){
        this.data = array != null ? array : new ArrayList<>();
    }

    public List<String> Value(){
        return this.data;
    }

    // region ConvertTo

    public String ToJoin(String speater){
        if (this.data.isEmpty()) return "";
        return ArrayUtil.join(this.data.toArray(), speater);
    }

    public String ToJoin(){
        return this.ToJoin(",");
    }

    // endregion
}
