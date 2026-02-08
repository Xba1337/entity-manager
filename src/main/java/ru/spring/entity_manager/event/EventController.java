package ru.spring.entity_manager.event;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @GetMapping
    public List<EventDto> getEvents(@Valid EventSearchFilter eventSearchFilter) {
        log.info("Get events from {}", eventSearchFilter);

        return eventService.getAllEvents(eventSearchFilter)
                .stream()
                .map(eventMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public EventDto getEvent(@PathVariable Integer id) {
        log.info("Get event from {}", id);

        return eventMapper.toDto(eventService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody @Valid EventCreationRequest eventDto) {
        log.info("Create event: {}", eventDto);
        Event createdEvent = eventService.createEvent(eventDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventMapper.toDto(createdEvent));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @eventSecurity.isOwner(#id, authentication)")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        log.info("Delete event: {}", id);
        eventService.deleteEventById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @eventSecurity.isOwner(#id, authentication)")
    public EventDto updateEvent(@PathVariable Integer id, @RequestBody @Valid EventDto eventDto) {
        log.info("Update event: {}", eventDto);
        Event updatedEvent = eventService.updateEvent(id, eventMapper.toDomain(eventDto));

        return eventMapper.toDto(updatedEvent);
    }
}
