package com.smt.modules.commands.createuser;

import com.smt.libs.domain.usecases.BaseCommand;
import com.smt.libs.domain.valueobjects.ULID;
import com.smt.libs.domain.valueobjects.UseCaseContext;

public class CreateUserCommand extends BaseCommand {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final Integer age;
    public CreateUserCommand(UseCaseContext context,
                             String id,
                             String firstName,
                             String lastName,
                             Integer age) {
        super(context);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

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
}
