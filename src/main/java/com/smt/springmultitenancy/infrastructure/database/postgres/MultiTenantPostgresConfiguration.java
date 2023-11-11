package com.smt.springmultitenancy.infrastructure.database.postgres;

import com.smt.springmultitenancy.infrastructure.TenantProperties;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.entry;

@Configuration
@EnableConfigurationProperties({TenantProperties.class})
@EnableR2dbcRepositories
@EnableTransactionManagement
@ConditionalOnProperty(value = "smt.configuration.database.postgres.enabled", havingValue = "true")
public class MultiTenantPostgresConfiguration extends AbstractR2dbcConfiguration {
    private final TenantProperties tenantProperties;

    public MultiTenantPostgresConfiguration(TenantProperties tenantProperties) {
        this.tenantProperties = tenantProperties;
    }

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        var connectionFactory = getConnectionFactory();
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    private AbstractRoutingConnectionFactory getConnectionFactory() {
        var routingConnectionFactory = new TenantAwareConnectionFactory();
        routingConnectionFactory.setLenientFallback(false);
        routingConnectionFactory.setTargetConnectionFactories(this.initializeTenantConnections());
        return routingConnectionFactory;
    }

    private Map<String, ConnectionFactory> initializeTenantConnections() {
        return tenantProperties.getPostgres()
                .entrySet()
                .stream()
                .map(tenant -> entry(tenant.getKey(), this.createConnectionFactory(tenant.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private ConnectionFactory createConnectionFactory(PostgresTenant postgresTenant) {
        var connectionFactory = new PostgresqlConnectionFactory(this.getPostgresqlConnectionConfiguration(postgresTenant));
        var connectionPoolConfiguration = ConnectionPoolConfiguration.builder(connectionFactory).build();
        return new ConnectionPool(connectionPoolConfiguration);
    }

    private PostgresqlConnectionConfiguration getPostgresqlConnectionConfiguration(PostgresTenant postgresTenant) {
        return PostgresqlConnectionConfiguration.builder()
                .host(postgresTenant.getHost())
                .port(postgresTenant.getPort())
                .database(postgresTenant.getDatabase())
                .schema(postgresTenant.getSchema())
                .username(postgresTenant.getUsername())
                .password(postgresTenant.getPassword())
                .build();
    }
}
