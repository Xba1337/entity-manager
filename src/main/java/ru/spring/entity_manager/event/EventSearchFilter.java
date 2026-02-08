package ru.spring.entity_manager.event;

import java.time.LocalDateTime;

public record EventSearchFilter(
        String name,
        LocalDateTime eventBeforeDate,
        LocalDateTime eventAfterDate,
        Integer capacityMin,
        Integer capacityMax,
        Integer priceMin,
        Integer priceMax,
        Integer durationMin,
        Integer durationMax,
        EventStatus eventStatus
) {
}
