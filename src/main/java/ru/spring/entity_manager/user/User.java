package ru.spring.entity_manager.user;

public record User(
        Integer id,
        String login,
        String passwordHash,
        String role
) {
}
