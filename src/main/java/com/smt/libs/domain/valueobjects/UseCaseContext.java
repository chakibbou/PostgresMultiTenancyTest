package com.smt.libs.domain.valueobjects;

public record UseCaseContext(String tenantId,
                             ULID sessionId,
                             String appId) {

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
