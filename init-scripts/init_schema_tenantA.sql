CREATE SCHEMA IF NOT EXISTS "tenantA";
SET SCHEMA 'tenantA';
CREATE TABLE IF NOT EXISTS "user" (
    id VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    age INT,
    metadata JSONB
);