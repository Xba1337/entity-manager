package ru.spring.entity_manager.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Integer> {

    @Query(value = """
            SELECT e FROM EventEntity e
            WHERE (:name IS NULL OR e.name LIKE %:name%)
            AND (CAST(:eventBeforeDate as date) IS NULL OR e.date <= :eventBeforeDate)
            AND (CAST(:eventAfterDate as date) IS NULL OR e.date >= :eventAfterDate)
            AND (:capacityMin IS NULL OR e.capacity >= :capacityMin)
            AND (:capacityMax IS NULL OR e.capacity <= :capacityMax)
            AND (:priceMin IS NULL OR e.price >= :priceMin)
            AND (:priceMax IS NULL OR e.price <= :priceMax)
            AND (:durationMin IS NULL OR e.duration >= :durationMin)
            AND (:durationMax IS NULL OR e.duration <= :durationMax)
            AND (:eventStatus IS NULL OR e.status = :eventStatus)
            """)
    List<EventEntity> findEvents(
            @Param("name") String name,
            @Param("eventBeforeDate") LocalDateTime eventBeforeDate,
            @Param("eventAfterDate") LocalDateTime eventAfterDate,
            @Param("capacityMin") Integer capacityMin,
            @Param("capacityMax") Integer capacityMax,
            @Param("priceMin") Integer priceMin,
            @Param("priceMax") Integer priceMax,
            @Param("durationMin") Integer durationMin,
            @Param("durationMax") Integer durationMax,
            @Param("eventStatus") EventStatus eventStatus
    );

    boolean existsByIdAndOwner_Login(Integer id, String login);

    @Modifying
    @Query(value = """
            UPDATE EventEntity e
            SET e.status = ru.spring.entity_manager.event.EventStatus.STARTED
            WHERE e.status = ru.spring.entity_manager.event.EventStatus.SCHEDULED
            AND :now >= e.date
            AND :now < e.date + e.duration * 1 minute
            """)
    int updateEventsStatusesFromScheduledToStarted(@Param("now") LocalDateTime now);

    @Modifying
    @Query(value = """
            UPDATE EventEntity e
            SET e.status = ru.spring.entity_manager.event.EventStatus.COMPLETED
            WHERE e.status = ru.spring.entity_manager.event.EventStatus.STARTED
            AND :now >= e.date + e.duration * 1 minute 
            """)
    int updateEventsStatusesFromStartedToCompleted(@Param("now") LocalDateTime now);
}
