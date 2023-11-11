package com.smt.modules.errors;

import com.smt.libs.domain.Exception.ExceptionBase;

public class UserIdEmptyError extends ExceptionBase {
    private static final String ERROR_CODE = "USER.ID_EMPTY";

    public UserIdEmptyError() {
        super(ERROR_CODE);
    }
}
