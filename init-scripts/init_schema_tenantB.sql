CREATE SCHEMA IF NOT EXISTS "tenantB";
SET SCHEMA 'tenantB';
CREATE TABLE IF NOT EXISTS "user" (
    id VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    age INT,
    metadata JSONB
);