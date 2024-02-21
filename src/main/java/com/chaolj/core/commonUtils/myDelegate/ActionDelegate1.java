package com.chaolj.core.commonUtils.myDelegate;

import java.io.Serializable;

public interface ActionDelegate1<T> extends Serializable {
    void invoke(T t);
}
