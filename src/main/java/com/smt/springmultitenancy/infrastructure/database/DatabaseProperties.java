package com.smt.springmultitenancy.infrastructure.database;

import com.smt.springmultitenancy.infrastructure.database.mongo.MongoTenant;
import com.smt.springmultitenancy.infrastructure.database.postgres.PostgresTenant;

import java.util.Map;

public class DatabaseProperties {
    private Map<String, PostgresTenant> postgres;
    private Map<String, MongoTenant> mongo;

    public Map<String, PostgresTenant> getPostgres() {
        return postgres;
    }

    public DatabaseProperties setPostgres(Map<String, PostgresTenant> postgres) {
        this.postgres = postgres;
        return this;
    }

    public Map<String, MongoTenant> getMongo() {
        return mongo;
    }

    public DatabaseProperties setMongo(Map<String, MongoTenant> mongo) {
        this.mongo = mongo;
        return this;
    }
}
