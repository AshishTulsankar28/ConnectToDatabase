/**
 * 
 */
package com.example.demo.event.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import com.example.demo.dto.OutboxEvent;

/**
 * @author Ashish Tulsankar
 * 31-Aug-2020
 */
@Component
public class EventPublisher implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher eventPublisher;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher=applicationEventPublisher;
	}
	
	
	 /**
     * This method publishes the event on to listeners configured by "@EventListener".
     *
     * @param outboxEvent
     */
    public void fireEvent(OutboxEvent outboxEvent) {
        this.eventPublisher.publishEvent(outboxEvent);
    }

}
