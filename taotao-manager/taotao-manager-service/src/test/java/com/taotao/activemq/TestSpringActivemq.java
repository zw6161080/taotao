//package com.taotao.activemq;
//
//import javax.jms.Destination;
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessageCreator;
//
//public class TestSpringActivemq {
//	
//	//使用jmsTemplate发送消息
//	@Test
//	public void testSpringActivemq() throws Exception{
//		
//		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
//		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
//		Destination destination=(Destination) applicationContext.getBean("test-queue");
//		jmsTemplate.send(destination, new MessageCreator() {
//			
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				TextMessage textMessage = session.createTextMessage("hello spring activemq");
//				return textMessage;
//			}
//		});
//		
//	}
//
//}