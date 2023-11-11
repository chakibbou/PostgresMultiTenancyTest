package com.smt.modules.commands.createuser;

import com.smt.libs.domain.usecases.RestBaseController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/{tenantId}")
public class CreateUserRestController extends RestBaseController {
    private final CreateUserCommandHandler createUserCommandHandler;

    public CreateUserRestController(CreateUserCommandHandler createUserCommandHandler) {
        this.createUserCommandHandler = createUserCommandHandler;
    }

    @PostMapping("/users")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> createUser(@PathVariable("tenantId") String tenantId,
                                 @RequestBody CreateUserHttpRequest createUserHttpRequest) {
        return createUserCommandHandler.handle(
                new CreateUserCommand(
                        this.generateUseCaseContext(tenantId),
                        createUserHttpRequest.id(),
                        createUserHttpRequest.firstName(),
                        createUserHttpRequest.lastName(),
                        createUserHttpRequest.age()
                )
        );
    }
}
