package ru.spring.entity_manager.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import ru.spring.entity_manager.event.EventEntity;

import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @Size(min = 4)
    private String login;

    @Column(nullable = false, name = "password")
    @Size(min = 4)
    private String passwordHash;

    @OneToMany(mappedBy = "owner")
    private List<EventEntity> events;

    private String role;

    public UserEntity(Integer id, String login, String passwordHash, String role,List<EventEntity> events) {
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
        this.role = role;
        this.events = events;
    }

    public UserEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(List<EventEntity> events) {
        this.events = events;
    }
}
