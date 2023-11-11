package com.smt.modules.commands.createuser;

public record CreateUserHttpRequest(String id,
                                    String firstName,
                                    String lastName,
                                    Integer age) {
}
