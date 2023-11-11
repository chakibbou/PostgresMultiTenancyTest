package com.smt.libs.domain.usecases;

import com.smt.infrastructure.database.TenantContext;
import com.smt.libs.domain.valueobjects.SMTLogger;
import reactor.core.publisher.Mono;

public abstract class BaseCommandHandler<T extends BaseCommand> {
    protected abstract Mono<Void> execute(T command);

    public Mono<Void> handle(T command) {
        var executionMono = this.execute(command)
                .contextWrite(TenantContext.withTenantId(command.getTenantId()))
                .doFirst(() -> this.getLogger()
                        .info(command.getContext(), "[command handler: %s] is executing [command: %s]", this.getClass().getSimpleName(), command));
        return executionMono.doOnError(
                error -> this.getLogger().error(
                        command.getContext(),
                        error,
                        "An error has been detected when [command handler: %s] is executing [command: %s]",
                        this.getClass().getSimpleName(),
                        command
                )
        );
    }

    protected SMTLogger getLogger() {
        return SMTLogger.getLogger(this.getClass());
    }
}
