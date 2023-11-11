package com.smt.modules.commands.createuser;

import com.smt.libs.domain.usecases.BaseCommandHandler;
import com.smt.libs.domain.valueobjects.ULID;
import com.smt.modules.domain.entities.UserEntity;
import com.smt.modules.errors.UserAlreadyExistsError;
import com.smt.modules.errors.UserIdEmptyError;
import com.smt.modules.errors.UserIdInvalidError;
import com.smt.modules.ports.UserRepositoryPort;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateUserCommandHandler extends BaseCommandHandler<CreateUserCommand> {

    private final UserRepositoryPort userRepository;

    public CreateUserCommandHandler(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected Mono<Void> execute(CreateUserCommand command) {
        return this.validate(command)
                .then(Mono.defer(() -> this.createUser(this.getUserEntity(command))));
    }

    private Mono<Void> createUser(UserEntity userEntity) {
        return this.userRepository.create(userEntity);
    }

    private Mono<Void> validate(CreateUserCommand command) {
        this.checkUserIdIsNotEmpty(command.getId());
        this.checkIdFormat(command.getId());
        return this.checkUserExistence(command);
    }

    private Mono<Void> checkUserExistence(CreateUserCommand command) {
        return this.userRepository.existsById(new ULID(command.getId()))
                .handle((exists, sink) -> {
                    if (exists) {
                        sink.error(new UserAlreadyExistsError());
                    }
                });
    }

    private void checkIdFormat(String id) {
        try {
            new ULID(id);
        } catch (Exception e) {
            throw new UserIdInvalidError();
        }
    }

    private void checkUserIdIsNotEmpty(String id) {
        if (Strings.isEmpty(id)) {
            throw new UserIdEmptyError();
        }
    }

    private UserEntity getUserEntity(CreateUserCommand command) {
        return new UserEntity(
                new ULID(command.getId()),
                command.getFirstName(),
                command.getLastName(),
                command.getAge()
        );
    }
}
