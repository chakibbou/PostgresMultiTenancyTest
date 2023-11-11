package com.smt.modules.domain.entities;

import com.smt.libs.domain.entities.BaseEntity;
import com.smt.libs.domain.valueobjects.ULID;

public class UserEntity extends BaseEntity<ULID> {
    private final String firstName;
    private final String lastName;
    private final Integer age;

    public UserEntity(ULID id,
                      String firstName,
                      String lastName,
                      Integer age) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }
}
