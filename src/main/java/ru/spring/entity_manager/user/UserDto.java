package ru.spring.entity_manager.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

public record UserDto(
        @Null
        Integer id,

        @NotBlank
        @Size(min = 4)
        String login,

        @NotBlank
        @Size(min = 4)
        String passwordHash,

        @NotBlank
        String role
) {
}
