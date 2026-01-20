package ru.spring.entity_manager.event;

import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event toDomain(EventEntity eventEntity) {
        if (eventEntity == null) {
            return null;
        }

        return new Event(
                eventEntity.getId(),
                eventEntity.getDate(),
                eventEntity.getLocationId(),
                eventEntity.getCapacity(),
                eventEntity.getPrice(),
                eventEntity.getDuration(),
                eventEntity.getStatus(),
                eventEntity.getName()
        );
    }

    public Event toDomain(EventDto eventDto) {
        if (eventDto == null) {
            return null;
        }

        return new Event(
                eventDto.id(),
                eventDto.date(),
                eventDto.locationId(),
                eventDto.capacity(),
                eventDto.price(),
                eventDto.duration(),
                eventDto.status(),
                eventDto.name()
        );
    }

    public EventEntity toEntity(Event event) {
        if (event == null) {
            return null;
        }

        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(event.id());
        eventEntity.setDate(event.date());
        eventEntity.setLocationId(event.locationId());
        eventEntity.setCapacity(event.capacity());
        eventEntity.setPrice(event.price());
        eventEntity.setDuration(event.duration());
        eventEntity.setStatus(event.status());
        eventEntity.setName(event.name());
        return eventEntity;
    }

    public EventDto toDto(Event event) {
        if (event == null) {
            return null;
        }

        return new EventDto(
                event.id(),
                event.date(),
                event.locationId(),
                event.capacity(),
                event.price(),
                event.duration(),
                event.status(),
                event.name()
        );
    }
}
