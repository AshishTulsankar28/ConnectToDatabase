package com.tibco.demo;

import static org.assertj.core.api.Assertions.assertThat;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@DirtiesContext
public class SpringJmsApplicationTest {

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void testIntegrationGateway() {
	  
    ConnectionFactory bean =
    applicationContext.getBean("connectionFactory",
        ConnectionFactory.class);
    JmsMessagingTemplate msgTemplate = 
    		applicationContext.getBean("jmsMessagingTemplate",
    				JmsMessagingTemplate.class);
	  
    try {
		Connection conn = bean.createConnection();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
	
		
		Queue q = session.createQueue("demo.test");
		
		msgTemplate.setDefaultDestination(q);
		msgTemplate.convertAndSend("Hi");
		
		
		
    } catch (JMSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//    MessageChannel outboundOrderRequestChannel =
//        applicationContext.getBean("outboundOrderRequestChannel",
//            MessageChannel.class);
//    QueueChannel outboundOrderResponseChannel = applicationContext
//        .getBean("outboundOrderResponseChannel", QueueChannel.class);
//
//    outboundOrderRequestChannel
//        .send(new GenericMessage<>("order-001"));
//
//    assertThat(
//        outboundOrderResponseChannel.receive(5000).getPayload())
//            .isEqualTo("Accepted");;
  }
}
