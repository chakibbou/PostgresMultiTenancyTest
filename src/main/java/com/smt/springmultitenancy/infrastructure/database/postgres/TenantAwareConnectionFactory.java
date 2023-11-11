package com.smt.springmultitenancy.infrastructure.database.postgres;

import io.r2dbc.spi.ConnectionFactoryMetadata;
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

import static com.smt.springmultitenancy.infrastructure.database.TenantContext.TENANT_ID;

public class TenantAwareConnectionFactory extends AbstractRoutingConnectionFactory {

    static final class PostgresqlConnectionFactoryMetadata implements ConnectionFactoryMetadata {

        static final PostgresqlConnectionFactoryMetadata INSTANCE = new PostgresqlConnectionFactoryMetadata();

        public static final String NAME = "PostgreSQL";

        private PostgresqlConnectionFactoryMetadata() {
        }

        @Override
        public String getName() {
            return NAME;
        }
    }
    @Override
    protected Mono<Object> determineCurrentLookupKey() {
        return Mono.deferContextual(Mono::just)
                .filter(contextView -> contextView.hasKey(TENANT_ID))
                .map(contextView -> contextView.get(TENANT_ID))
                .transform(objectMono -> errorIfEmpty(objectMono, () -> new RuntimeException(String.format("ContextView does not contain the Lookup Key '%s'", TENANT_ID))));
    }

    public static <R> Mono<R> errorIfEmpty(Mono<R> mono, Supplier<Throwable> throwableSupplier) {
        return mono.switchIfEmpty(Mono.defer(() -> Mono.error(throwableSupplier.get())));
    }

    @Override
    public ConnectionFactoryMetadata getMetadata() {
        return PostgresqlConnectionFactoryMetadata.INSTANCE;
    }
}
