package com.smt.modules.database.user;

import com.smt.libs.domain.valueobjects.ULID;
import com.smt.modules.domain.entities.UserEntity;
import com.smt.modules.ports.UserRepositoryPort;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class UserInMemoryRepository implements UserRepositoryPort {
    private final List<UserEntity> savedEntities = new ArrayList<>();

    @Override
    public Mono<Void> create(UserEntity userEntity) {
        this.savedEntities.add(userEntity);
        return Mono.empty();
    }

    public Mono<UserEntity> findById(ULID id) {
        return Mono.just(this.savedEntities.stream()
                .filter(userEntity -> userEntity.getId().equals(id))
                .findFirst()
                .orElseThrow());
    }

    @Override
    public Mono<Boolean> existsById(ULID userId) {
        return Mono.just(this.savedEntities.stream()
                .anyMatch(userEntity -> userEntity.getId().equals(userId)));
    }
}
