package com.backend.votingservice.service;


import com.backend.votingservice.dto.UserFavoriteMoviesMessage;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static org.springframework.amqp.rabbit.connection.NodeLocator.LOGGER;
import java.util.logging.Logger;


@AllArgsConstructor
@Service
public class MessageReceiverService {

    PartyService partyService;
    private static final Logger logger = Logger.getLogger(MessageReceiverService.class.getName());

    @RabbitListener(queues = "myQueue")
    public void receiveMessage(String message) {
        logger.info("Received message: " + message);
    }

/*    @RabbitListener(queues = "myQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }*/



    public void consumeJsonMessage(UserFavoriteMoviesMessage message){
        LOGGER.info(String.format("Received JSON message -> %s", message.toString()));
    }

    @RabbitListener(queues = "favoritesToVotingQueue")
    public void receiveFavoriteMoviesMessage(UserFavoriteMoviesMessage message) {
        partyService.processFavoriteMoviesMessage(message);
    }
}
