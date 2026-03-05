package ru.spring.entity_manager.event;

import org.springframework.stereotype.Component;
import ru.spring.entity_manager.event.notification.FieldChange;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class EventDifferencesUtil {

    public EventDifferencesUtil() {
    }

    public Map<String, FieldChange<?>> changeMap(Event oldEvent, Event updatedEvent) {
        Map<String, FieldChange<?>> changes = new HashMap<>();

        putIfChanged(changes, "date", oldEvent.date(), updatedEvent.date());
        putIfChanged(changes, "locationId", oldEvent.locationId(), updatedEvent.locationId());
        putIfChanged(changes, "capacity", oldEvent.capacity(), updatedEvent.capacity());
        putIfChanged(changes, "price", oldEvent.price(), updatedEvent.price());
        putIfChanged(changes, "duration", oldEvent.duration(), updatedEvent.duration());
        putIfChanged(changes, "status", oldEvent.status(), updatedEvent.status());
        putIfChanged(changes, "name", oldEvent.name(), updatedEvent.name());

        return changes;
    }

    private <T> void putIfChanged(
            Map<String, FieldChange<?>> changes,
            String fieldName,
            T oldValue,
            T newValue) {
        if (!Objects.equals(oldValue, newValue)) {
            changes.put(fieldName, new FieldChange<>(oldValue, newValue));
        }
    }
}
