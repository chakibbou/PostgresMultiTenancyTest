package com.smt.modules.builders;

import com.github.javafaker.Faker;
import com.smt.libs.domain.valueobjects.ULID;
import com.smt.modules.domain.entities.UserEntity;
import com.smt.modules.ports.UserRepositoryPort;

public class UserBuilder extends AbstractBuilder {
    private final UserRepositoryPort userRepository;
    private ULID id;

    public UserBuilder(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    public static UserBuilder builder(UserRepositoryPort userRepository) {
        return new UserBuilder(userRepository);
    }

    public UserBuilder withId(ULID userId) {
        this.id = userId;
        return this;
    }

    public UserEntity build() {
        var userEntity = new UserEntity(
                this.orElse(this.id, ULID.generate()),
                Faker.instance().bothify("???????"),
                Faker.instance().bothify("???????"),
                18
        );
        this.userRepository.create(userEntity)
                .log("User Created")
                .block();
        return userEntity;
    }
}
