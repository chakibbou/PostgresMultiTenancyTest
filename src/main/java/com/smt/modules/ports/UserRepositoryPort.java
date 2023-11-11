package com.smt.modules.ports;

import com.smt.libs.domain.valueobjects.ULID;
import com.smt.modules.domain.entities.UserEntity;
import reactor.core.publisher.Mono;

public interface UserRepositoryPort {
    Mono<Void> create(UserEntity userEntity);
    Mono<UserEntity> findById(ULID userId);
    Mono<Boolean> existsById(ULID userId);
}
