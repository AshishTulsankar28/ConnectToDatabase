/**
 * 
 */
package com.tibco.demo.service;

import javax.jms.Message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.tibco.demo.TibcoConstants;

import lombok.SneakyThrows;

/**
 * @author Ashish Tulsankar
 * 15-Sep-2020
 */

@Service
public class TibcoListener {

	private static final Logger logger = LogManager.getLogger(TibcoListener.class);

	/**
	 * concurrency = 1 for topic listeners because publisher will be unaware of subscribers/listeners.
	 * And many times, to balance the load in case of heavy message traffic, it will be scaled automatically
	 * and we might listen the same message for multiple times so listen with concurrency 1 to ensure that we only listen to message once
	 * @param message
	 */
	@JmsListener(destination = TibcoConstants.INCOMING_TOPIC, containerFactory = "topicJmsListenerContainerFactory", concurrency = "1")
	public void processTopicMessages(Message message) {

		logger.info("Topic ~ JMS Message received {}",message);

	}


	@SneakyThrows
	@JmsListener(destination = TibcoConstants.INCOMING_QUEUE, containerFactory = "queueJmsListenerContainerFactory")
	public void processQueueMessages(Message message) {

		logger.info("Queue ~ JMS Message received {}", message);
		logger.info("Queue ~ This is important {}", message.getIntProperty("JMSXDeliveryCount"));

	}
	
	// Configure multiple listeners at once
	//	@JmsListeners(value ={
	//			@JmsListener(destination = TibcoConstants.INCOMING_TOPIC, containerFactory = "topicJmsListenerContainerFactory"),
	//			@JmsListener(destination = TibcoConstants.INCOMING_QUEUE, containerFactory = "queueJmsListenerContainerFactory")
	//			})
	//	public void processMessages(Message message) {
	//		
	//		logger.info("Message received {}", message);
	//
	//	}



}
