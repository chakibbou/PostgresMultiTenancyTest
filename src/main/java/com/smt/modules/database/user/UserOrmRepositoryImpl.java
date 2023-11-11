package com.smt.modules.database.user;

import com.smt.libs.domain.valueobjects.ULID;
import com.smt.modules.domain.entities.UserEntity;
import com.smt.modules.ports.UserRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserOrmRepositoryImpl implements UserRepositoryPort {

    private final UserRepository userRepository;

    public UserOrmRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> create(UserEntity userEntity) {
        var userOrmEntity = new UserOrmEntity(userEntity).setNew(true);
        return this.userRepository.save(userOrmEntity)
                .then();
    }

    @Override
    public Mono<UserEntity> findById(ULID userId) {
        return this.userRepository.findById(userId.getValue())
                .map(UserOrmEntity::toEntity);
    }

    @Override
    public Mono<Boolean> existsById(ULID userId) {
        return this.userRepository.existsById(userId.getValue());
    }
}
