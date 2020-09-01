/**
 * 
 */
package com.example.demo.kafka.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Represents {@link Consumer}
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */
@Service
public class KafkaConsumerService {
	private static Logger log= LogManager.getLogger(KafkaConsumerService.class);
	
	@KafkaListener(topics= {"student-activity-log"},groupId = "kafka-consumer-group")
	public void consumeMsg(String msg) {
		log.info("Message Received by kafka consumer {} ",msg);
	}

}
