package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
	
	private static final String KAFKA_TOPIC = "kafka-test-topic";
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessage(String msg) {
		this.kafkaTemplate.send(KAFKA_TOPIC, msg);
	}

}
