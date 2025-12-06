package ru.spring.entity_manager.location;

public record Location(
        Integer id,
        String name,
        String address,
        Integer capacity,
        String description
) {
}
