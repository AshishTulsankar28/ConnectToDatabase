/**
 * 
 */
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.KafkaProducerService;

/**
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */
@RestController
public class KafkaController {
	
	private final KafkaProducerService kafkaProducerService;

	@Autowired
	public KafkaController(KafkaProducerService producer) {
		this.kafkaProducerService=producer;
	}

	@GetMapping("/kafka/ping")
	public String sendPing(@RequestParam String msg) {
		this.kafkaProducerService.sendMessage(msg);
		return "Success !";
	}
	
}
