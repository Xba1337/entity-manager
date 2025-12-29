package ru.spring.entity_manager.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(

        @NotBlank
        @Size(min = 4)
        String login,

        @NotBlank
        @Size(min = 4)
        String passwordHash
) {
}
