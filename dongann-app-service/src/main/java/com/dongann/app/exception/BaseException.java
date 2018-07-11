package com.dongann.app.exception;

public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message,new Throwable(message));
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}