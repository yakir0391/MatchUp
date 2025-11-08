package com.example.MatchUp.Kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserDeletedProducer {

    private final KafkaTemplate<String, Long> kafkaTemplate;

    public UserDeletedProducer(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserDeletedEvent(Long userId) {
        kafkaTemplate.send("user-deleted", userId);
        System.out.println("user-deleted sent successfully");
    }
}
