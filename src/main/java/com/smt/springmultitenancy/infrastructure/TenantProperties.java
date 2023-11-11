package com.smt.springmultitenancy.infrastructure;

import com.smt.springmultitenancy.infrastructure.database.DatabaseProperties;
import com.smt.springmultitenancy.infrastructure.database.mongo.MongoTenant;
import com.smt.springmultitenancy.infrastructure.database.postgres.PostgresTenant;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "smt")
public class TenantProperties {
    private List<String> tenants;
    private DatabaseProperties database;

    public List<String> getTenants() {
        return tenants;
    }

    public TenantProperties setTenants(List<String> tenants) {
        this.tenants = tenants;
        return this;
    }

    public DatabaseProperties getDatabase() {
        return database;
    }

    public TenantProperties setDatabase(DatabaseProperties database) {
        this.database = database;
        return this;
    }

    public Map<String, PostgresTenant> getPostgres() {
        return this.database.getPostgres();
    }

    public Map<String, MongoTenant> getMongo() {
        return this.database.getMongo();
    }
}
