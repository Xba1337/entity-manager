package ru.spring.entity_manager.user;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserInitializer {

    private static final Logger log = LoggerFactory.getLogger(DefaultUserInitializer.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DefaultUserInitializer(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        createDefaultAdmin();
        log.info("Admin initialized");

        createDefaultUser();
        log.info("User initialized");
    }

    private void createDefaultAdmin() {
        if (userService.userExistsByLogin("admin")){
            return;
        }

        User defaultAdmin = new User(
                null,
                "admin",
                passwordEncoder.encode("admin"),
                UserRole.ADMIN.name()
        );

        userService.saveUser(defaultAdmin);
    }

    private void createDefaultUser() {
        if (userService.userExistsByLogin("user")){
            return;
        }

        User defaultUser = new User(
                null,
                "user",
                passwordEncoder.encode("user"),
                UserRole.USER.name()
        );

        userService.saveUser(defaultUser);
    }
}
