package ru.spring.entity_manager.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityConverter userEntityConverter;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserEntityConverter userEntityConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userEntityConverter = userEntityConverter;
    }

    public User singUpUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByLogin(signUpRequest.login())){
            throw new IllegalArgumentException("Login already exists");
        }

        String encode = passwordEncoder.encode(signUpRequest.passwordHash());
        UserEntity userEntity = new UserEntity(
                null,
                signUpRequest.login(),
                encode,
                UserRole.USER.name(),
                List.of()
        );

        UserEntity saved = userRepository.save(userEntity);

        return new User(
                saved.getId(),
                saved.getLogin(),
                encode,
                saved.getRole()
        );
    }

    public boolean userExistsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    public void saveUser(User user) {
        UserEntity userEntity = userEntityConverter.convertToEntity(user);
        userRepository.save(userEntity);
    }
}
