package com.yibi.evidence.chain.exception;

/**
 *存证异常类
 *@author yibi
 *@date 2021-06-24
 */
public class EviException extends RuntimeException {

    public EviException(String message) {
        super(message);
    }

    public EviException(String message, Throwable cause) {
        super(message, cause);
    }

    public EviException(Throwable cause) {
        super(cause);
    }
}
