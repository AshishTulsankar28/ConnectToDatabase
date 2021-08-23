/**
 * 
 */
package com.tibco.demo.service;

import javax.jms.MessageProducer;

import org.springframework.jms.core.JmsMessagingTemplate;

import com.tibco.demo.views.Student;

/**
 * @author Ashish Tulsankar
 * 15-Sep-2020
 */
public interface TibcoService {
	
	/**
	 * Send message using {@link MessageProducer}
	 * @param student as message to send over destination
	 */
	public void sendMessage(Student student);
	
	/**
	 * Send message using {@link JmsMessagingTemplate}
	 * @param student as Message content
	 */
	public void sendViaMsgTemplate(Student student);
}
