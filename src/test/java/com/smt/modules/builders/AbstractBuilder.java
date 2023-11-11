package com.smt.modules.builders;

import java.util.Objects;

abstract class AbstractBuilder {
    final String NULL_VALUE = "NULL";

    <T> T orElse(T value, T defaultValue) {
        if (Objects.isNull(value)) return defaultValue;
        return this.isNullValueRequired(value) ? null : value;
    }

    private <T> boolean isNullValueRequired(T value) {
        return this.NULL_VALUE.equals(value);
    }
}
