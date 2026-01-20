package ru.spring.entity_manager.registration;

import org.springframework.stereotype.Component;
import ru.spring.entity_manager.event.EventEntity;

@Component
public class RegistrationMapper {

    public Registration toDomain(RegistrationEntity registrationEntity) {
        if (registrationEntity == null) {
            return null;
        }

        return new Registration(
                registrationEntity.getId(),
                registrationEntity.getEvent().getId(),
                registrationEntity.getUserId()
        );
    }

    public Registration toDomain(RegistrationDto registrationDto) {
        if (registrationDto == null) {
            return null;
        }

        return new Registration(
                registrationDto.id(),
                registrationDto.eventId(),
                registrationDto.userId()
        );
    }

    public RegistrationEntity toEntity(Registration registration, EventEntity eventEntity) {
        if (registration == null || eventEntity == null) {
            return null;
        }

        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setId(registration.id());
        registrationEntity.setEvent(eventEntity);

        return registrationEntity;
    }


    public RegistrationDto toDto(Registration registration) {
        return new RegistrationDto(
                registration.id(),
                registration.eventId(),
                registration.userId()
        );
    }
}
