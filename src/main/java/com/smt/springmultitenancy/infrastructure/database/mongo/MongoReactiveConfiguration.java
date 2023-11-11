package com.smt.springmultitenancy.infrastructure.database.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.smt.springmultitenancy.infrastructure.TenantProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static com.smt.springmultitenancy.infrastructure.database.TenantContext.TENANT_ID;

@EnableConfigurationProperties({TenantProperties.class})
@Configuration
@ConditionalOnProperty(value = "smt.configuration.database.mongo.enabled", havingValue = "true")
public class MongoReactiveConfiguration {
    private final TenantProperties tenantProperties;
    private final Map<String, MongoClient> tenantClients = new HashMap<>();
    private MongoTenant defaultTenant;

    public MongoReactiveConfiguration(TenantProperties tenantProperties) {
        this.tenantProperties = tenantProperties;
    }

    @PostConstruct
    @Lazy
    public void initTenant() {
        tenantProperties.getMongo()
                .forEach((key, value) -> tenantClients.put(key, createMongoClient(value)));
        tenantProperties.getMongo().entrySet().stream().findFirst().ifPresent(t -> defaultTenant = t.getValue());
    }

    @Bean
    public String databaseName() {
        return this.defaultTenant.getDatabase();
    }

    @Bean
    public MongoClient createMongoClient() {
        return createMongoClient(defaultTenant);
    }

    public Mono<MongoDatabase> mongoDatabaseCurrentTenantResolver() {
        return Mono.deferContextual(Mono::just)
                .filter(contextView -> contextView.hasKey(TENANT_ID))
                .map(contextView -> contextView.get(TENANT_ID))
                .map(tenantId -> {
                    var currentTenant = getCurrentTenant((String) tenantId);
                    return tenantClients.get((String) tenantId).getDatabase(currentTenant.getDatabase());
                });
    }

    private MongoTenant getCurrentTenant(String tenantId) {
        return tenantProperties.getMongo().entrySet()
                .stream()
                .filter(tenant -> tenant.getKey().equals(tenantId))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new Error(String.format("Tenant ID not found %s", tenantId)));
    }

    private MongoClient createMongoClient(MongoTenant mongoTenant) {
        var mongoSettingsBuilder = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoTenant.getUri()));
        return MongoClients.create(mongoSettingsBuilder.build());
    }
}
