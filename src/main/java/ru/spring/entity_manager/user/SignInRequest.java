package ru.spring.entity_manager.user;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(

        @NotBlank
        String login,

        @NotBlank
        String passwordHash
) {
}
