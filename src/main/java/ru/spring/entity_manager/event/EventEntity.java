package ru.spring.entity_manager.event;

import jakarta.persistence.*;
import ru.spring.entity_manager.registration.RegistrationEntity;
import ru.spring.entity_manager.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer id;

    @Column(name = "date_time")
    private LocalDateTime date;

    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "max_location_capacity")
    private Integer capacity;

    @Column(name = "ticket_price")
    private Integer price;

    @Column(name = "event_duration")
    private Integer duration;

    @Column(name = "event_status")
    private EventStatus status;

    @Column(name = "event_name")
    private String name;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistrationEntity> registrations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    public EventEntity() {
    }

    public EventEntity(Integer id, LocalDateTime date, Integer locationId, Integer capacity, Integer price, Integer duration, EventStatus status, String name, List<RegistrationEntity> registrations, UserEntity owner) {
        this.id = id;
        this.date = date;
        this.locationId = locationId;
        this.capacity = capacity;
        this.price = price;
        this.duration = duration;
        this.status = status;
        this.name = name;
        this.registrations = registrations;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime dateTime) {
        this.date = dateTime;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<RegistrationEntity> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<RegistrationEntity> registrations) {
        this.registrations = registrations;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public EventEntity copy() {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(id);
        eventEntity.setDate(date);
        eventEntity.setLocationId(locationId);
        eventEntity.setCapacity(capacity);
        eventEntity.setPrice(price);
        eventEntity.setDuration(duration);
        eventEntity.setStatus(status);
        eventEntity.setName(name);
        eventEntity.setRegistrations(registrations);
        eventEntity.setOwner(owner);
        return eventEntity;
    }
}
