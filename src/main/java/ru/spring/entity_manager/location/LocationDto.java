package ru.spring.entity_manager.location;

import jakarta.validation.constraints.*;

record LocationDto(

        @Null
        Integer id,

        @NotBlank
        String name,

        @NotBlank
        String address,

        @NotNull
        @Positive
        @Min(5)
        Integer capacity,

        String description
) {
}
