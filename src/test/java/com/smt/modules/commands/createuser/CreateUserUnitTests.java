package com.smt.modules.commands.createuser;

import com.github.javafaker.Faker;
import com.smt.libs.domain.valueobjects.ULID;
import com.smt.libs.domain.valueobjects.UseCaseContext;
import com.smt.modules.builders.UseCaseContextBuilder;
import com.smt.modules.builders.UserBuilder;
import com.smt.modules.database.user.UserInMemoryRepository;
import com.smt.modules.errors.UserAlreadyExistsError;
import com.smt.modules.errors.UserIdEmptyError;
import com.smt.modules.errors.UserIdInvalidError;
import com.smt.modules.ports.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateUserUnitTests {
    private CreateUserCommandHandler createUserCommandHandler;
    private UserRepositoryPort userRepository;

    @BeforeEach
    public void setup() {
        this.userRepository = new UserInMemoryRepository();
        this.createUserCommandHandler = new CreateUserCommandHandler(this.userRepository);
    }

    @Test
    void shouldReturnAnErrorIfTheUserIdIsEmpty() {
        // Given
        final var command = new CreateUserCommand(
                this.generateUseCaseContext(),
                "",
                Faker.instance().bothify("?????"),
                Faker.instance().bothify("???????"),
                18
        );

        // Then
        assertThrows(UserIdEmptyError.class, () ->
                // When
                this.createUserCommandHandler.handle(command).block()
        );
    }

    @Test
    void shouldReturnAnErrorIfTheUserIdIsInvalid() {
        // Given
        final var command = new CreateUserCommand(
                this.generateUseCaseContext(),
                Faker.instance().bothify("?????????"),
                Faker.instance().bothify("?????"),
                Faker.instance().bothify("???????"),
                18
        );

        // Then
        assertThrows(UserIdInvalidError.class, () ->
                // When
                this.createUserCommandHandler.handle(command).block()
        );
    }

    @Test
    void shouldReturnAnErrorIfTheUserAlreadyExists() {
        // Given
        var userId = ULID.generate();
        UserBuilder.builder(this.userRepository)
                .withId(userId)
                .build();
        final var command = new CreateUserCommand(
                this.generateUseCaseContext(),
                userId.getValue(),
                Faker.instance().bothify("?????"),
                Faker.instance().bothify("???????"),
                18
        );

        // Then
        assertThrows(UserAlreadyExistsError.class, () ->
                // When
                this.createUserCommandHandler.handle(command).block()
        );
    }

    @Test
    void shouldCreateUser() {
        // Given
        final var command = new CreateUserCommand(
                this.generateUseCaseContext(),
                ULID.generate().getValue(),
                Faker.instance().bothify("?????"),
                Faker.instance().bothify("???????"),
                18
        );

        // When
        this.createUserCommandHandler.handle(command).block();

        // Then
        var userEntity = ((UserInMemoryRepository) this.userRepository).findById(new ULID(command.getId())).block();
        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getFirstName()).isEqualTo(command.getFirstName());
        assertThat(userEntity.getLastName()).isEqualTo(command.getLastName());
        assertThat(userEntity.getAge()).isEqualTo(command.getAge());
    }

    private UseCaseContext generateUseCaseContext() {
        return UseCaseContextBuilder.builder()
                .withTenant("tenantA")
                .build();
    }
}
