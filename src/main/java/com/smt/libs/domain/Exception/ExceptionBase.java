package com.smt.libs.domain.Exception;

public class ExceptionBase extends RuntimeException {
    private final String errorCode;

    public ExceptionBase(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ExceptionBase(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ExceptionBase(String errorCode, String message, Object... args) {
        super(String.format(message, args));
        this.errorCode = errorCode;
    }

    public ExceptionBase(String errorCode, String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
