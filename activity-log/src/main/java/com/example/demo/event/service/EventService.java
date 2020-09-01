/**
 * 
 */
package com.example.demo.event.service;

import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OutboxEvent;
import com.example.demo.model.Outbox;
import com.example.demo.repository.OutboxRepository;


/**
 * @author Ashish Tulsankar
 * 31-Aug-2020
 */
@Service
public class EventService {

	private static Logger log= LogManager.getLogger(EventService.class);
    private final OutboxRepository outBoxRepository;


    @Autowired
    public EventService(OutboxRepository outBoxRepository) {
        this.outBoxRepository = outBoxRepository;
    }

    /**
     * Notify the events being published 
     * @param event
     */
    @EventListener
    public void handleOutboxEvent(OutboxEvent event) {

        UUID uuid = UUID.randomUUID();
        Outbox entity = new Outbox(
                uuid,
                event.getAggregateId(),
                event.getEventType(),
                event.getPayload().toString(),
                new Date()
        );

        log.info("Handling event : {}.", entity);

        outBoxRepository.save(entity);
        //TODO After outbox table has been modified, change should be updated over kafka topic automatically.

        //Delete the event once written, so that the table doesn't grow.
        //outBoxRepository.delete(entity);
    }
	
}
