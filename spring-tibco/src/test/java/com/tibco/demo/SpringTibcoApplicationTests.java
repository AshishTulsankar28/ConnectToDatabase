package com.tibco.demo;

import static org.junit.jupiter.api.Assertions.fail;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tibco.demo.service.TibcoService;
import com.tibco.demo.views.Student;

// Integration test with Tibco. This needs tibco server up & running on localhost:7222
@SpringBootTest
class SpringTibcoApplicationTests {

	@Autowired
	TibcoService tibcoQueueServiceImpl;
	
	@Test
	void contextLoads() {
		
	}
	
	@Test
	void sendMsgs() {
		try {
			Student stud= new Student();
			stud.setId(1);
			stud.setFirstName("Ashish");
			tibcoQueueServiceImpl.sendMessage(stud);
		} catch (Exception e) {
			System.out.println("What's the problem "+ e.getMessage());
			fail();
		}
	}

}
