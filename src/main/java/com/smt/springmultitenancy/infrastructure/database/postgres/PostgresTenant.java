package com.smt.springmultitenancy.infrastructure.database.postgres;

public class PostgresTenant {
    private String host;
    private Integer port;
    private String database;
    private String schema;
    private String username;
    private String password;

    public String getHost() {
        return host;
    }

    public PostgresTenant setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public PostgresTenant setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getDatabase() {
        return database;
    }

    public PostgresTenant setDatabase(String database) {
        this.database = database;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public PostgresTenant setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public PostgresTenant setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PostgresTenant setPassword(String password) {
        this.password = password;
        return this;
    }
}
