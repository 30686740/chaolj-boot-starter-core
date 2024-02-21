package com.chaolj.core.commonUtils.myDelegate;

import java.io.Serializable;

public interface FunctionDelegate1<F, T> extends Serializable {
    F invoke(T t);
}
