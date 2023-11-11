package com.smt.springmultitenancy.infrastructure.database.mongo;

public class MongoTenant {
    private String uri;
    private String database;

    public String getUri() {
        return uri;
    }

    public MongoTenant setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getDatabase() {
        return database;
    }

    public MongoTenant setDatabase(String database) {
        this.database = database;
        return this;
    }
}
