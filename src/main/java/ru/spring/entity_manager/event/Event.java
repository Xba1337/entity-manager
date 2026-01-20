package ru.spring.entity_manager.event;

import java.time.LocalDateTime;

public record Event(
        Integer id,
        LocalDateTime date,
        Integer locationId,
        Integer capacity,
        Integer price,
        Integer duration,
        EventStatus status,
        String name
) {
}
