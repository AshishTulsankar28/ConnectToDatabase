package com.example.demo.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.grpc.service.ClientServiceImpl;
import com.example.demo.model.Student;

/**
 * Manages the {@link RequestMapping} for {@link Student} related activities.
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */
@RestController
public class StudentController{
	
	private static Logger log= LogManager.getLogger(StudentController.class);
	
	@Autowired
	private ClientServiceImpl grpcClient;
	
	@GetMapping(value = "/dummy/student/{id}")
	public String ping(@PathVariable int id) {
		log.info("API CALL SUCCESSFUL. RECEIVED ID: {}",id);
		return this.grpcClient.ping(id);
	}
	
	@PostMapping(value = "/student")
	public int saveStudent(@RequestBody Map<String,String> studentInfo ) {
		return this.grpcClient.saveStudent(studentInfo);
	}
	
	@GetMapping(value = "/kafka/publish")
	public void publishDbChanges() {
		this.grpcClient.publishChanges();
	}

}
