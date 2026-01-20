package ru.spring.entity_manager.user;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.entity_manager.security.jwt.AuthenticationService;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserDtoConverter userDtoConverter;

    public UserController(UserService userService, AuthenticationService authenticationService, UserDtoConverter userDtoConverter) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.userDtoConverter = userDtoConverter;
    }

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid SignUpRequest signUpRequest) {
        log.info("Registering user: {}", signUpRequest.login());

        User user = userService.singUpUser(signUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDtoConverter.convertToDto(user));
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtTokenResponse> authenticateUser(@RequestBody @Valid SignInRequest signInRequest) {
        log.info("Authenticating user: {}", signInRequest.login());
        String token = authenticationService.authenticate(signInRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new JwtTokenResponse(token));
    }
}
