package com.smt.libs.domain.Exception;

public class InvalidULIDFormatError extends ExceptionBase {
    private static final String ERROR_CODE = "ID.INVALID_ULID_FORMAT";

    public InvalidULIDFormatError() {
        super(ERROR_CODE);
    }
}
