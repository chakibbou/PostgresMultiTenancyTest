package com.smt.modules.database.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smt.libs.database.BaseOrmEntity;
import com.smt.libs.domain.valueobjects.ULID;
import com.smt.modules.domain.entities.UserEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "users")
public class UserOrmEntity extends BaseOrmEntity implements Persistable<String> {

    @Id
    private String id;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column("age")
    private Integer age;
    @Transient
    @JsonIgnore
    private boolean isNew;

    public UserOrmEntity() {
    }

    public UserOrmEntity(UserEntity userEntity) {
        this.id = userEntity.getId().getValue();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.age = userEntity.getAge();
    }

    public static UserEntity toEntity(UserOrmEntity userOrmEntity) {
        return new UserEntity(
                new ULID(userOrmEntity.getId()),
                userOrmEntity.getFirstName(),
                userOrmEntity.getLastName(),
                userOrmEntity.getAge()
        );
    }

    @Override
    public String getId() {
        return id;
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

    @Override
    public boolean isNew() {
        return isNew;
    }

    public UserOrmEntity setNew(boolean aNew) {
        isNew = aNew;
        return this;
    }
}
