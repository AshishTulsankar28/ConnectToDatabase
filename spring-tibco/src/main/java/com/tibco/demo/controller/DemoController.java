/**
 * 
 */
package com.tibco.demo.controller;

import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tibco.demo.service.TibcoService;
import com.tibco.demo.views.Student;

/**
 * Exposed end-point just for demo purpose.
 * @author Ashish Tulsankar
 * 15-Sep-2020
 */
@RestController
public class DemoController {

	private static final Logger logger = LogManager.getLogger(DemoController.class);

	@Autowired
	private TibcoService tibcoTopicServiceImpl;
	@Autowired
	private TibcoService tibcoQueueServiceImpl;

	/**
	 * @apiNote Sends the incoming message pay-load using {@link MessageProducer}
	 * @param destination represents the target destination (either {@link Topic} or {@link Queue}) for incoming message
	 * @param student as message pay-load.  
	 */
	@PostMapping(value = "/producer/send/{destination}")
	public void sendUsingMsgProducer(@PathVariable String destination, @RequestBody Student student) {
		logger.info("Using Message Producer");

		if(destination.equalsIgnoreCase("topic")) {
			tibcoTopicServiceImpl.sendMessage(student);
		}else {
			tibcoQueueServiceImpl.sendMessage(student);
		}

	}

	/**
	 *  @apiNote Sends the incoming message pay-load using {@link JmsMessagingTemplate}
	 * @param destination represents the target destination (either {@link Topic} or {@link Queue}) for incoming message
	 * @param student as message pay-load.
	 */
	@PostMapping(value = "/template/send/{destination}")
	public void sendUsingTemplate(@PathVariable String destination, @RequestBody Student student) {
		logger.info("Using JmsMessagingTemplate");

		if(destination.equalsIgnoreCase("topic")) {
			tibcoTopicServiceImpl.sendViaMsgTemplate(student);
		}else {
			tibcoQueueServiceImpl.sendViaMsgTemplate(student);
		}

	}

}
