package com.backend.votingservice.service;


import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageSenderService {
    private final RabbitTemplate rabbitTemplate;

    public void sendPartyCreationTrigger(Long message) {
        rabbitTemplate.convertAndSend("votingToFavoritesQueue", message);
    }
}
