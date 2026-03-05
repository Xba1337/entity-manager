package ru.spring.entity_manager.registration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.entity_manager.event.*;
import ru.spring.entity_manager.user.UserEntity;
import ru.spring.entity_manager.user.UserRepository;

import java.util.List;

@Service
@Transactional
public class RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
    private final RegistrationMapper registrationMapper;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    public RegistrationService(RegistrationRepository registrationRepository, EventRepository eventRepository, RegistrationMapper registrationMapper, UserRepository userRepository, EventMapper eventMapper) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
        this.registrationMapper = registrationMapper;
        this.userRepository = userRepository;
        this.eventMapper = eventMapper;
    }

    @Transactional(readOnly = true)
    public List<Event> getAllRegistrations() {
        log.info("Finding all registrations from database");

        Integer authenticatedUserOrAdminId = getAuthenticatedUserOrAdminId();

        List<EventEntity> registrationEntities = registrationRepository.findUserRegisteredEvents(
                authenticatedUserOrAdminId
        );

        return registrationEntities.stream()
                .map(eventMapper::toDomain)
                .toList();
    }

    public Registration registerUser(Integer eventId) {
        log.info("Registering user");

        EventEntity eventEntity = verifyingEventExistence(eventId);
        Integer authenticatedUserOrAdminId = getAuthenticatedUserOrAdminId();

        if (!eventEntity.getStatus().equals(EventStatus.SCHEDULED)) {
            throw new IllegalArgumentException("Event registration error: %s. Event %s. Registration is only possible for events with the %s status."
                    .formatted(eventEntity.getName(), EventStatus.getStatusAsString(eventEntity.getStatus()), EventStatus.getStatusAsString(EventStatus.SCHEDULED)));
        }

        if (eventEntity.getCapacity() == eventEntity.getRegistrations().size()) {
            throw new IllegalArgumentException("Event registration error: %s. There are no available seats"
                    .formatted(eventEntity.getName()));
        }

        eventEntity.getRegistrations().stream()
                .filter(r -> r.getUserId().equals(authenticatedUserOrAdminId))
                .findAny()
                .ifPresent(r -> {
                    throw new IllegalArgumentException("User with id:%s is already registered for the event"
                            .formatted(authenticatedUserOrAdminId));
                });

        RegistrationEntity registrationEntity = new RegistrationEntity(
                null,
                eventEntity,
                authenticatedUserOrAdminId
        );

        RegistrationEntity saved = registrationRepository.save(registrationEntity);

        log.info("User is registered. Registration id:{} ", registrationEntity.getId());

        return registrationMapper.toDomain(saved);
    }

    public void cancelRegistration(Integer eventId) {
        log.info("Cancelling registration");

        EventEntity eventEntity = verifyingEventExistence(eventId);
        Integer authenticatedUserOrAdminId = getAuthenticatedUserOrAdminId();

        RegistrationEntity registrationEntity = registrationRepository.findRegistration(authenticatedUserOrAdminId, eventId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User with id:%s is not registered for the event".formatted(authenticatedUserOrAdminId)));
        registrationRepository.delete(registrationEntity);

        log.info("Registration is cancelled. Registration id:{} ", eventEntity.getId());
    }

    private EventEntity verifyingEventExistence(Integer eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Event with id:%s not found".formatted(eventId)));
    }

    private Integer getAuthenticatedUserOrAdminId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        return userRepository.findByLogin(login)
                .orElseThrow(() ->
                        new IllegalStateException("The authenticated user is missing from the database"))
                .getId();
    }

    public List<String> getUsersRegisteredOnEvent(Integer eventId) {
        return registrationRepository.findUsersIdRegisteredOnEvent(eventId)
                .stream()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() ->
        new IllegalArgumentException("User with id:%s not found".formatted(userId))))
                .map(UserEntity::getLogin)
                .toList();
    }
}
