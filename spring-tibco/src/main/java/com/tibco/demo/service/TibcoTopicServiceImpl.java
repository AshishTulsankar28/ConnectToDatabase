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
public class TibcoTopicServiceImpl implements TibcoService{

	private static final Logger logger = LogManager.getLogger(TibcoTopicServiceImpl.class);


	private ConnectionFactory connectionFactory;
	private JmsMessagingTemplate jmsMessagingTemplate;

	private MessageProducer messageProducer;
	private Connection connection;
	private Session session;

	@Autowired
	public TibcoTopicServiceImpl(ConnectionFactory connectionFactory,
			JmsMessagingTemplate messagingTemplate) {

		this.connectionFactory=connectionFactory;
		this.jmsMessagingTemplate=messagingTemplate;
	}

	/**
	 * Method to initialize the {@link Session} & {@link Connection} via configured {@link ConnectionFactory}
	 * <br> Builds {@link MessageProducer} for {@link Topic}
	 * <br> For more details,
	 * Refer <a href="https://docs.oracle.com/javaee/7/api/javax/jms/Connection.html#createSession-boolean-int-">createSession</a>
	 * @return {@link Session}
	 */
//	@PostConstruct
//	public void init() {
//
//		try {
//
//			logger.info("Topic Init");
//			// #1 Initialize messageProducer with Topic as destination 
//			connection  = connectionFactory.createConnection();
//			session     = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
//			Topic topic = session.createTopic(TibcoConstants.INCOMING_TOPIC);
//			messageProducer = session.createProducer(topic);
//
//			// #2 Initialize messagingTemplate with Topic as destination 
//			jmsMessagingTemplate.setDefaultDestination(topic);
//
//			connection.start();
//
//		} catch (JMSException e) {
//			logger.error("Exception while starting the connection {}", e);
//		}
//
//	}
//

	@Override
	public void sendMessage(Student student) {
		try {

			// Send Text Message
			Message message= session.createTextMessage(student.toString());
			messageProducer.send(message);


		} catch (JMSException e) {
			logger.error("Exception occurred {}", e);
		}

	}


	public void sendViaMsgTemplate(Student student) {

		jmsMessagingTemplate.convertAndSend(student.toString());

	}

}
