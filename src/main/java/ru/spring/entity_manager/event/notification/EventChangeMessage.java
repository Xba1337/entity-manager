package ru.spring.entity_manager.event.notification;

import java.util.List;
import java.util.Map;

public record EventChangeMessage(
        Integer eventId,
        Integer changedByUserId,
        Integer ownerId,
        Map<String, FieldChange<?>> changes,
        List<String> participants
) {
}