package com.smt.infrastructure.database;

import reactor.util.context.Context;

public class TenantContext {
    public static final String TENANT_ID = "TENANT_ID";

    public static Context withTenantId(String id) {
        return Context.of(TENANT_ID, id);
    }
}
