package com.smt.libs.domain.usecases;

import com.smt.libs.domain.valueobjects.Objects;
import com.smt.libs.domain.valueobjects.UseCaseContext;

public class BaseCommand {
    private final UseCaseContext context;

    public BaseCommand(UseCaseContext context) {
        this.context = context;
    }

    public UseCaseContext getContext() {
        return context;
    }

    public String getTenantId() {
        return this.context.tenantId();
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
