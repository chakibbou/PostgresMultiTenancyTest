package com.smt.libs.domain.entities;

import com.smt.libs.domain.valueobjects.ID;

import java.util.Objects;

public class BaseEntity<T extends ID> {
    protected final T id;

    public BaseEntity(T id) {
        this.id = id;
    }

    public T getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return this.id != null ? this.id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return com.smt.libs.domain.valueobjects.Objects.toString(this);
    }
}
