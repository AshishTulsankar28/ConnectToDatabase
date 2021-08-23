/**
 * 
 */
package com.tibco.demo.service;

import javax.annotation.PostConstruct;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.tibco.demo.TibcoConstants;
import com.tibco.demo.views.Student;



/**
 * Include methods to send messages over {@link Topic}
 * @author Ashish Tulsankar
 * 14-Sep-2020
 */
@Service
public class TibcoQueueServiceImpl implements TibcoService{

	private static final Logger logger = LogManager.getLogger(TibcoQueueServiceImpl.class);

	private ConnectionFactory connectionFactory;
	private JmsMessagingTemplate jmsMessagingTemplate;

	private MessageProducer messageProducer;
	private Connection connection;
	private Session session;

	@Autowired
	public TibcoQueueServiceImpl(ConnectionFactory factory,
			JmsMessagingTemplate messagingTemplate) {
		this.connectionFactory=factory;
		this.jmsMessagingTemplate=messagingTemplate;
	}

	/**
	 * Method to initialize the {@link Session} & {@link Connection} via configured {@link ConnectionFactory}.
	 * <br> builds {@link MessageProducer} for {@link Queue}
	 * <br> For more details,
	 * Refer <a href="https://docs.oracle.com/javaee/7/api/javax/jms/Connection.html#createSession-boolean-int-">createSession</a>
	 * @return {@link Session}
	 */
	@PostConstruct
	public void init() {

		try {

			logger.info("Queue Init");
			// #1 Initialize messageProducer with Queue as destination 
			connection  = connectionFactory.createConnection();	
			session     = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
		
			Queue queue = session.createQueue(TibcoConstants.INCOMING_QUEUE);
			
			messageProducer = session.createProducer(queue);

			// #2 Initialize messagingTemplate with Queue as destination 
			jmsMessagingTemplate.setDefaultDestination(queue);

			connection.start();
		} catch (JMSException e) {
			logger.error("Exception while creating connection {}", e);
		}
	}


	@Override
	public void sendMessage(Student student) {
		try {

			// Send Text Message
			Message message= session.createTextMessage(student.toString());
			//session.create
			messageProducer.send(message);
			
		} catch (JMSException e) {
			logger.error("Exception occurred {}", e);
		}

	}

	@Override
	public void sendViaMsgTemplate(Student student) {
		jmsMessagingTemplate.convertAndSend(student);
		//jmsMessagingTemplate.convertAndSend(student.toString());

	}


}
