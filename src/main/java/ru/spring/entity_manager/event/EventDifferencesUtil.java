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
        if (!Objects.equals(oldEvent.date(), updatedEvent.date())) {
            changes.put(
                    "date",
                    new FieldChange<>(
                            oldEvent.date(),
                            updatedEvent.date()
                    )
            );
        }
        if (!Objects.equals(oldEvent.locationId(), updatedEvent.locationId())) {
            changes.put(
                    "locationId",
                    new FieldChange<>(
                            oldEvent.locationId(),
                            updatedEvent.locationId()
                    )
            );
        }
        if (!Objects.equals(oldEvent.capacity(), updatedEvent.capacity())) {
            changes.put(
                    "capacity",
                    new FieldChange<>(
                            oldEvent.capacity(),
                            updatedEvent.capacity()
                    )
            );
        }
        if (!Objects.equals(oldEvent.price(), updatedEvent.price())) {
            changes.put(
                    "price",
                    new FieldChange<>(
                            oldEvent.price(),
                            updatedEvent.price()
                    )
            );
        }
        if (!Objects.equals(oldEvent.duration(), updatedEvent.duration())) {
            changes.put(
                    "duration",
                    new FieldChange<>(
                            oldEvent.duration(),
                            updatedEvent.duration()
                    )
            );
        }
        if (!Objects.equals(oldEvent.status(), updatedEvent.status())) {
            changes.put(
                    "status",
                    new FieldChange<>(
                            oldEvent.status(),
                            updatedEvent.status()
                    )
            );
        }
        if (!Objects.equals(oldEvent.name(), updatedEvent.name())) {
            changes.put(
                    "name",
                    new FieldChange<>(
                            oldEvent.name(),
                            updatedEvent.name()
                    )
            );
        }
        return changes;
    }
}
