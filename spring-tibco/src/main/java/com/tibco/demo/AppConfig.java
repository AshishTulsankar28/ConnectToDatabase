/**
 * 
 */
package com.tibco.demo;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.tibco.demo.service.SampleJmsErrorHandler;
import com.tibco.tibjms.TibjmsConnectionFactory;

/**
 * Represents Tibco configuration class
 * @author Ashish Tulsankar
 * @implNote
 * Comment Queue listener container factory in case you want to listen to the same queue 
 * in application as well as in Apache Kafka since Queue represents P2P communication
 * & In current scenario, we want <b>APACHE KAFKA</b> to listen to {@link Queue}.<br> 
 * So, If we subscribe to Let's say demo.incomingQueue listener in application, we will be unable to listen 
 * to event in kafka from same Queue.
 * <br><i>One solution is to create a bridge (from demo.incomingQueue) to another let's say demo.outgoingQueue & configure the connector to listen to 
 * demo.outgoingQueue for Kafka-Connect. This way, you could listen to demo.incomingQueue inside your application and to demo.outgoingQueue
 * in kafka with same message content. </i>
 * <br>14-Sep-2020
 */
@Configuration
@EnableJms
@EnableTransactionManagement
public class AppConfig {
	
	@Autowired
	SampleJmsErrorHandler sampleJmsErrorHandler;

	/**
	 * Read properties from application.yml & build {@link ConnectionFactory}
	 * @return {@link ConnectionFactory} for Tibco EMS 
	 */
	@Bean
	public ConnectionFactory connectionFactory(){

		// 		Now, reading connection properties from application.yml
		//		TibjmsConnectionFactory connectionFactory = new TibjmsConnectionFactory("tcp://localhost:7222");
		//		connectionFactory.setUserName("admin");
		//		connectionFactory.setUserPassword("admin");

		return new TibjmsConnectionFactory();
	}

	/**
	 * To enable Transaction management
	 * @return {@link JmsTransactionManager}
	 */
	@Bean
	public JmsTransactionManager jmsTransactionManager() {

		return new JmsTransactionManager(connectionFactory());
	}


	/**
	 * Implicitly builds {@link JmsTemplate}
	 * @return {@link JmsMessagingTemplate} using {@link ConnectionFactory}
	 */
	@Bean
	public JmsMessagingTemplate jmsMessagingTemplate() {

		return new JmsMessagingTemplate(connectionFactory());

	}

	/**
	 * Builds listener container definition for {@link Topic}
	 * @implNote Topic ~ One to Many | Many to Many communication<br>
	 * @return {@link DefaultJmsListenerContainerFactory}
	 */
	@Bean(name = "topicJmsListenerContainerFactory")
	public DefaultJmsListenerContainerFactory topicJmsListenerContainerFactory() {

		DefaultJmsListenerContainerFactory topicListenerFactory = new DefaultJmsListenerContainerFactory();
		topicListenerFactory.setConnectionFactory(connectionFactory());
		topicListenerFactory.setTransactionManager(jmsTransactionManager());
		topicListenerFactory.setPubSubDomain(true); 
		topicListenerFactory.setSessionTransacted(true);	
		topicListenerFactory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
		topicListenerFactory.setConcurrency("5");
		topicListenerFactory.setErrorHandler(sampleJmsErrorHandler);


		return topicListenerFactory;
	}

	/**
	 * Builds container definition for {@link Queue}
	 * @implNote Queue ~ Point to point communication<br>
	 * @return {@link DefaultJmsListenerContainerFactory}
	 */
		@Bean(name = "queueJmsListenerContainerFactory")
		public DefaultJmsListenerContainerFactory queueJmsListenerContainerFactory() {
	
			DefaultJmsListenerContainerFactory queueListenerFactory = new DefaultJmsListenerContainerFactory();
			queueListenerFactory.setConnectionFactory(connectionFactory());
			queueListenerFactory.setTransactionManager(jmsTransactionManager());
			queueListenerFactory.setPubSubDomain(false); 
			queueListenerFactory.setSessionTransacted(true);		
			queueListenerFactory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
			queueListenerFactory.setConcurrency("5");
			queueListenerFactory.setErrorHandler(sampleJmsErrorHandler);
	
			return queueListenerFactory;
		}
}
