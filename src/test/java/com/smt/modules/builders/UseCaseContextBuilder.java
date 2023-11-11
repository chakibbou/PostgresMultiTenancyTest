package com.smt.modules.builders;

import com.smt.libs.domain.valueobjects.ULID;
import com.smt.libs.domain.valueobjects.UseCaseContext;

public class UseCaseContextBuilder extends AbstractBuilder{
    private String tenantId;

    public UseCaseContextBuilder() {
    }

    public static UseCaseContextBuilder builder() {
        return new UseCaseContextBuilder();
    }

    public UseCaseContextBuilder withTenant(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public UseCaseContext build() {
        return new UseCaseContext(
                this.orElse(this.tenantId, "testTenant"),
                ULID.generate(),
                "Postgres Multi Tenancy Test"
        );
    }
}
