package com.smt.libs.domain.valueobjects;

import com.smt.libs.domain.Exception.EmptyIdError;
import org.apache.logging.log4j.util.Strings;

public abstract class ID {
    private final String value;

    protected abstract void validate(String value);

    protected ID(String value) {
        this.checkIfEmpty(value);
        this.validate(value);
        this.value = value;
    }

    private void checkIfEmpty(String value) {
        if (Strings.isEmpty(value)) {
            throw new EmptyIdError();
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ID id = (ID) o;

        return java.util.Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
