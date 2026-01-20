package ru.spring.entity_manager.event;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record EventDto(

        @Null
        Integer id,

        @NotNull(message = "Date is mandatory")
        LocalDateTime date,

        @NotNull(message = "Id of location cannot be null")
        Integer locationId,

        @Positive(message = "Capacity should be more than 0")
        @NotNull(message = "Capacity is mandatory")
        Integer capacity,

        @PositiveOrZero(message = "Price cannot be negative")
        @NotNull(message = "Price is mandatory")
        Integer price,

        @Min(value = 1,message = "The time must be at least 30 minutes")
        @NotNull(message = "Duration is mandatory")
        Integer duration,

        @NotNull(message = "Event status is mandatory")
        EventStatus status,

        @NotNull(message = "Event name is mandatory")
        String name
) {
}
