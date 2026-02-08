package ru.spring.entity_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EntityManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntityManagerApplication.class, args);
    }

}
