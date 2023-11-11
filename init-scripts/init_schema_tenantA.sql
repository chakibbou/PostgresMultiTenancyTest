CREATE SCHEMA IF NOT EXISTS "tenantA";
SET SCHEMA 'tenantA';
CREATE TABLE IF NOT EXISTS "users" (
    id CHARACTER VARYING(100) PRIMARY KEY,
    first_name CHARACTER VARYING(100),
    last_name CHARACTER VARYING(100),
    age INT,
    metadata JSONB,
    created_date timestamp with time zone,
    updated_date timestamp with time zone
);