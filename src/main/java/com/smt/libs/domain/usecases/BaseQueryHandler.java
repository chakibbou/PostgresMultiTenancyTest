package com.smt.libs.domain.usecases;

import com.smt.infrastructure.database.TenantContext;
import com.smt.libs.domain.valueobjects.SMTLogger;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class BaseQueryHandler<T extends BaseQuery, S> {
    protected abstract Publisher<S> execute(T query);

    public Flux<S> handleFlux(T query) {
        var executionFlux = Flux.from(this.execute(query))
                .contextWrite(TenantContext.withTenantId(query.getTenantId()))
                .doFirst(() -> this.getLogger()
                        .info(query.getContext(), "[query handler: %s] is executing [query: %s]", this.getClass().getSimpleName(), query));
        return executionFlux.doOnError(
                error -> this.getLogger().error(
                        query.getContext(),
                        error,
                        "An error has been detected when [query handler: %s] is executing [query: %s]",
                        this.getClass().getSimpleName(),
                        query
                )
        );
    }

    public Mono<S> handleMono(T query) {
        var executionMono = Mono.from(this.execute(query))
                .contextWrite(TenantContext.withTenantId(query.getTenantId()))
                .doFirst(() -> this.getLogger()
                        .info(query.getContext(), "[query handler: %s] is executing [query: %s]", this.getClass().getSimpleName(), query));
        return executionMono.doOnError(
                error -> this.getLogger().error(
                        query.getContext(),
                        error,
                        "An error has been detected when [query handler: %s] is executing [query: %s]",
                        this.getClass().getSimpleName(),
                        query
                )
        );
    }

    protected SMTLogger getLogger() {
        return SMTLogger.getLogger(this.getClass());
    }
}
