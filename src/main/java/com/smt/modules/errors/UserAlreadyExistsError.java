package com.smt.modules.errors;

import com.smt.libs.domain.Exception.ExceptionBase;

public class UserAlreadyExistsError extends ExceptionBase {
    private static final String ERROR_CODE = "USER.ALREADY_EXISTS";

    public UserAlreadyExistsError() {
        super(ERROR_CODE);
    }
}
