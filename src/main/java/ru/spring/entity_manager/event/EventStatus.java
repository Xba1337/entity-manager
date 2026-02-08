package ru.spring.entity_manager.event;

public enum EventStatus {
    CANCELED,
    SCHEDULED,
    STARTED,
    COMPLETED;

    public static String getStatusAsString(EventStatus status) {
        return switch (status) {
            case CANCELED -> "\"Отменено\"";
            case SCHEDULED -> "\"Запланировано\"";
            case STARTED -> "\"Началось\"";
            case COMPLETED -> "\"Завершено\"";
        };
    }
}
