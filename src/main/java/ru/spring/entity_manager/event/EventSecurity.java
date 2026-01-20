package ru.spring.entity_manager.event;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class EventSecurity {

    private final EventRepository eventRepository;

    public EventSecurity(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public boolean isOwner(Integer eventId, Authentication authentication) {
        return eventRepository.existsByIdAndOwner_Login(eventId, authentication.getName());
    }
}
