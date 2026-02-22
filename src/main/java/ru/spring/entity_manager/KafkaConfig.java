package ru.spring.entity_manager;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.spring.entity_manager.event.notification.EventChangeMessage;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaTemplate<Integer, EventChangeMessage> kafkaTemplate(
            KafkaProperties kafkaProperties
    ) {
        var properties = kafkaProperties.buildProducerProperties(
                new DefaultSslBundleRegistry()
        );
        ProducerFactory<Integer, EventChangeMessage> pf =
                new DefaultKafkaProducerFactory<>(properties);

        return new KafkaTemplate<>(pf);
    }
}
