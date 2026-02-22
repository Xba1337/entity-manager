package ru.spring.entity_manager.registration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spring.entity_manager.event.EventEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Integer> {

    @Query(value = """
            SELECT r.event FROM RegistrationEntity r
            WHERE r.userId = :userId
            """)
    List<EventEntity> findUserRegisteredEvents(
            @Param("userId") Integer userId
    );

    @Query(value = """
            SELECT r FROM RegistrationEntity r
            WHERE r.event.id = :eventId
            AND r.userId = :userId
            """)
    Optional<RegistrationEntity> findRegistration(
            @Param("userId") Integer userId,
            @Param("eventId") Integer eventId
    );

    @Query(value = """
            SELECT r.userId from RegistrationEntity r
            where r.event.id = :eventId
            """)
    List<Integer> findUsersIdRegisteredOnEvent(
            @Param("eventId") Integer eventId
    );
}
