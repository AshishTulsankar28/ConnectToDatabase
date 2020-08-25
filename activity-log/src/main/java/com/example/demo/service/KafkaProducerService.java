/**
 * 
 */
package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Represents {@link Producer}
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */
@Service
public class KafkaProducerService {

	private static final String KAFKA_TOPIC = "kafka-test-topic";
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessage(String msg) {
		this.kafkaTemplate.send(KAFKA_TOPIC, msg);
	}
	
}
