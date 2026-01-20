package ru.spring.entity_manager.registration;

import jakarta.validation.constraints.NotNull;

public record RegistrationDto(
        @NotNull(message = "Id of registration cannot be null")
        Integer id,

        @NotNull(message = "Id of event cannot be null")
        Integer eventId,

        @NotNull(message = "Id of user cannot be null")
        Integer userId
) {
}
