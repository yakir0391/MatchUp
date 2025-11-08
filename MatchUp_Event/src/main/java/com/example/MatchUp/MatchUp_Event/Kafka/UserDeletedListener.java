package com.example.MatchUp.MatchUp_Event.Kafka;

import com.example.MatchUp.MatchUp_Event.Repository.EventRepository;
import org.apache.catalina.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserDeletedListener {

    private EventRepository eventRepo;

    public UserDeletedListener(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    @KafkaListener(topics = "user-deleted", groupId = "matchup-event-group")
    public void handleUserDeleted(Long userId) {
        System.out.println("kafka listener");
        eventRepo.deleteByOrganizerId(userId);
    }
}
