package ru.spring.entity_manager.registration;

import jakarta.persistence.*;
import ru.spring.entity_manager.event.EventEntity;

@Entity
@Table(name = "registrations")
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @Column(name = "user_id")
    private Integer userId;

    public RegistrationEntity() {
    }

    public RegistrationEntity(Integer id, EventEntity event, Integer userId) {
        this.id = id;
        this.event = event;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
