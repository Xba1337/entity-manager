package ru.spring.entity_manager.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.entity_manager.location.Location;
import ru.spring.entity_manager.location.LocationService;
import ru.spring.entity_manager.user.UserEntity;
import ru.spring.entity_manager.user.UserRepository;

import java.util.List;

@Service
@Transactional
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final LocationService locationService;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, EventMapper eventMapper, LocationService locationService, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.locationService = locationService;
        this.userRepository = userRepository;
    }

    public Event createEvent(EventCreationRequest request) {
        log.info("Creating new event");

        Location choosedLocation = locationService.getLocationById(request.locationId());

        if (choosedLocation.capacity() < request.capacity()) {
            throw new IllegalArgumentException("Location capacity: %s is not enough for the event, choose another location with a capacity greater than or equal to: %s"
                            .formatted(choosedLocation.capacity(), request.capacity()));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        UserEntity owner = userRepository.findByLogin(login)
                .orElseThrow(
                        () -> new IllegalStateException("The authenticated user is missing from the database")
                );

        EventEntity event = new EventEntity(
                null,
                request.date(),
                request.locationId(),
                request.capacity(),
                request.price(),
                request.duration(),
                EventStatus.SCHEDULED,
                request.name(),
                List.of(),
                owner
        );

        event = eventRepository.save(event);

        log.info("Created new event with id: {}", event.getId());

        return eventMapper.toDomain(event);
    }

    @Transactional(readOnly = true)
    public List<Event> getAllEvents(EventSearchFilter eventSearchFilter) {
        log.info("Getting all events from database");
        List<EventEntity> entities = eventRepository.findEvents(
                eventSearchFilter.name(),
                eventSearchFilter.eventBeforeDate(),
                eventSearchFilter.eventAfterDate(),
                eventSearchFilter.capacityMin(),
                eventSearchFilter.capacityMax(),
                eventSearchFilter.priceMin(),
                eventSearchFilter.priceMax(),
                eventSearchFilter.durationMin(),
                eventSearchFilter.durationMax(),
                eventSearchFilter.eventStatus()
        );

        return entities.stream()
                .map(eventMapper::toDomain)
                .toList();
    }

    @Transactional(readOnly = true)
    public Event getEventById(Integer id) {
        log.info("Getting event with id: {}", id);
        EventEntity eventEntity = eventRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Event with id:%s not found".formatted(id)));

        return eventMapper.toDomain(eventEntity);
    }

    public void deleteEventById(Integer id) {
        log.info("Deleting event with id: {}", id);

        EventEntity eventEntity = eventRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Event with id:%s not found".formatted(id)));

        eventRepository.delete(eventEntity);

        log.info("Deleted event with id: {}", id);
    }

    public Event updateEvent(Integer id, Event event) {
        log.info("Updating event with id: {}", id);

        EventEntity eventToUpdate= eventRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Event with id:%s not found".formatted(id)));

        if (eventToUpdate.getCapacity() < event.capacity()) {
            throw new IllegalArgumentException("The location cannot accommodate the number of people: %s. Location capacity is: %s."
                    .formatted(event.capacity(), eventToUpdate.getCapacity()));
        }

        eventToUpdate.setDate(event.date());
        eventToUpdate.setCapacity(event.capacity());
        eventToUpdate.setPrice(event.price());
        eventToUpdate.setDuration(event.duration());
        eventToUpdate.setStatus(event.status());
        eventToUpdate.setName(event.name());


        EventEntity updatedEvent = eventRepository.save(eventToUpdate);

        log.info("Updated event with id: {}", id);

        return eventMapper.toDomain(updatedEvent);
    }
}
