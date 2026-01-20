package ru.spring.entity_manager.user;

import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public User convertToModel(UserDto userDto) {
        return new User(
                userDto.id(),
                userDto.login(),
                userDto.passwordHash(),
                userDto.role()
        );
    }

    public UserDto convertToDto(User user) {
        return new UserDto(
                user.id(),
                user.login(),
                user.passwordHash(),
                user.role()
        );
    }
}
