package com.smt.libs.database;

import com.smt.libs.utils.json.JsonSerializer;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseOrmEntity {
    @CreatedDate
    @Column("created_date")
    protected OffsetDateTime createdDate;
    @LastModifiedDate
    @Column("updated_date")
    private OffsetDateTime updatedDate;
    @Column("metadata")
    private Json metadata;

    protected BaseOrmEntity() {
        this.createdDate = Instant.now().atOffset(ZoneOffset.UTC);
        this.setMetadata(new HashMap<>());
    }

    public Map<String, Object> getMetadata() {
        return JsonSerializer.toObject(this.metadata.asString(), Map.class);
    }

    public BaseOrmEntity setMetadata(Object metadata) {
        this.metadata = Json.of(JsonSerializer.serialize(metadata));
        return this;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public OffsetDateTime getUpdatedDate() {
        return updatedDate;
    }
}
