package ru.spring.entity_manager.user;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserEntityConverter {

    public User convertToModel(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getLogin(),
                userEntity.getPasswordHash(),
                userEntity.getRole()
        );
    }

    public UserEntity convertToEntity(User user) {
        return new UserEntity(
                user.id(),
                user.login(),
                user.passwordHash(),
                user.role(),
                List.of()
        );
    }
}
