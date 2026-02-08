package ru.spring.entity_manager.event;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public record EventCreationRequest(
        @NotNull(message = "Date is mandatory")
        LocalDateTime date,

        @NotNull(message = "Id of location cannot be null")
        Integer locationId,

        @Positive(message = "Capacity should be more than 0")
        Integer capacity,

        @PositiveOrZero(message = "Price cannot be negative")
        Integer price,

        @Min(value = 30,message = "The time must be at least 30 minutes")
        Integer duration,

        @NotNull(message = "Event name is mandatory")
        String name
) {
}
