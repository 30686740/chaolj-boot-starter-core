package com.chaolj.core.commonUtils.myDto;

import java.io.Serializable;

public class UIException extends RuntimeException implements Serializable {
    public UIException(String msg) {
        super(msg);
    }
}
