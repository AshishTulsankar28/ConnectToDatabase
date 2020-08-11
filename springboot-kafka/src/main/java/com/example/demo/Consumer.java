package com.example.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
	
	@KafkaListener(topics= {"kafka-test-topic"},groupId = "group_id")
	public void consumeMsg(String msg) {
		System.out.println("Message Received by kafka consumer- "+msg);
	}

}
