package com.chaolj.core.commonUtils.myDto;

import java.io.Serializable;

public class TokenException extends RuntimeException implements Serializable {
    public TokenException(String msg) {
        super(msg);
    }
}
