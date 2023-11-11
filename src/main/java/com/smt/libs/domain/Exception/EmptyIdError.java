package com.smt.libs.domain.Exception;

public class EmptyIdError extends ExceptionBase {
    private static final String ERROR_CODE = "ID.EMPTY_VALUE";

    public EmptyIdError() {
        super(ERROR_CODE);
    }
}

