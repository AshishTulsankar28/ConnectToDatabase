package com.example.demo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Student;
import com.example.demo.service.ClientService;

/**
 * Manages the {@link RequestMapping} for {@link Student} related activities.
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */
@RestController
public class StudentController{
	
	private static Logger log= LogManager.getLogger(StudentController.class);
	private ClientService clientService;
	

	@Autowired
	public  StudentController(ClientService client) {
		this.clientService=client;
	}
	
	@GetMapping(value = "/dummy/student/{id}")
	public String ping(@PathVariable int id) {
		log.info("SUCCESS. RECEIVED ID: {}",id);
		return this.clientService.ping(id);
	}
	
	@PostMapping(value = "/student")
	public void saveStudent() {
		this.clientService.saveStudent();
	}
	
	@GetMapping(value = "/kafka/publish")
	public void publishDbChanges() {
		this.clientService.publishChanges();
	}

}
