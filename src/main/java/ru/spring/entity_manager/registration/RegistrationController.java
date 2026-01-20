package ru.spring.entity_manager.registration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.spring.entity_manager.event.Event;
import ru.spring.entity_manager.event.EventDto;
import ru.spring.entity_manager.event.EventMapper;

import java.util.List;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    private final RegistrationService registrationService;
    private final RegistrationMapper registrationMapper;
    private final EventMapper eventMapper;

    public RegistrationController(RegistrationService registrationService, RegistrationMapper registrationMapper, EventMapper eventMapper) {
        this.registrationService = registrationService;
        this.registrationMapper = registrationMapper;
        this.eventMapper = eventMapper;
    }

    @GetMapping("/my")
    public ResponseEntity<List<EventDto>> getAllRegistrations() {
        log.info("Get all registrations for user");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication: {}", authentication);

        List<Event> events = registrationService.getAllRegistrations();

        return ResponseEntity.status(HttpStatus.OK)
                .body(events.stream()
                        .map(eventMapper::toDto)
                        .toList()
                );
    }

    @PostMapping("/{eventId}")
    public ResponseEntity<RegistrationDto> registerUser(@PathVariable Integer eventId) {
        log.info("Registering user to event with id: {}", eventId);
        Registration registration = registrationService.registerUser(eventId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registrationMapper.toDto(registration));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> cancelRegistration(@PathVariable Integer eventId) {
        log.info("Cancelling registration for event with id: {}", eventId);
        registrationService.cancelRegistration(eventId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
