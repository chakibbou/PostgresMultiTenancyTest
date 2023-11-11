package com.smt.libs.domain.usecases;

import com.smt.libs.domain.valueobjects.ULID;
import com.smt.libs.domain.valueobjects.UseCaseContext;
import org.springframework.beans.factory.annotation.Value;

public abstract class RestBaseController {
    @Value("{spring.application.name}")
    private String appId;

    protected UseCaseContext generateUseCaseContext(String tenantId) {
        return new UseCaseContext(
                tenantId,
                ULID.generate(),
                this.getAppId()
        );
    }

    public String getAppId() {
        return appId;
    }
}
