package ru.spring.entity_manager.util;

import java.time.LocalDateTime;

public record ErrorDto(
        String message,
        String detailMessage,
        LocalDateTime dateTime
) {
}
