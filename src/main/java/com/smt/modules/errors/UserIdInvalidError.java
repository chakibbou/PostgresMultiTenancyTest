package com.smt.modules.errors;

import com.smt.libs.domain.Exception.ExceptionBase;

public class UserIdInvalidError extends ExceptionBase {
    private final static String ERROR_CODE = "User.ID_INVALID";

    public UserIdInvalidError() {
        super(ERROR_CODE);
    }
}
