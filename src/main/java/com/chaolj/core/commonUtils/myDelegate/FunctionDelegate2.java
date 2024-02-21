package com.chaolj.core.commonUtils.myDelegate;

import java.io.Serializable;

public interface FunctionDelegate2<F, T1, T2> extends Serializable {
    F invoke(T1 t1, T2 t2);
}
