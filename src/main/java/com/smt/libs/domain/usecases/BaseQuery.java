package com.smt.libs.domain.usecases;

import com.smt.libs.domain.valueobjects.Objects;
import com.smt.libs.domain.valueobjects.UseCaseContext;

public abstract class BaseQuery {
    private final UseCaseContext context;

    public BaseQuery(UseCaseContext context) {
        this.context = context;
    }

    public UseCaseContext getContext() {
        return this.context;
    }

    public String getTenantId() {
        return this.context.tenantId();
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
