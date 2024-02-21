package com.chaolj.core.commonUtils.myDelegate;

import java.io.Serializable;

public interface ActionDelegate2<T1, T2> extends Serializable {
    void invoke(T1 t1, T2 t2);
}
