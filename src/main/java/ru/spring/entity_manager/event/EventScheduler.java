package ru.spring.entity_manager.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class EventScheduler {

    private static final Logger log = LoggerFactory.getLogger(EventScheduler.class);

    private final EventRepository eventRepository;

    public EventScheduler(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Scheduled(cron = "${scheduler.event.status.cron}")
    @Transactional
    public void updateEventStatus(){
        LocalDateTime now = LocalDateTime.now();

        int fromScheduledToStarted = eventRepository.updateEventsStatusesFromScheduledToStarted(now);
        int fromStartedToCompleted = eventRepository.updateEventsStatusesFromStartedToCompleted(now);

        if (fromScheduledToStarted > 0 || fromStartedToCompleted > 0) {
            log.info("Event status updated. To STARTED: {}. To COMPLETED: {}", fromScheduledToStarted, fromStartedToCompleted);
        }

        else log.info("There are no events to update");
    }
}
