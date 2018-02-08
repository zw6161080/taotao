//package com.taotao.activemq;
//
//import javax.jms.Connection;
//import javax.jms.ConnectionFactory;
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.MessageConsumer;
//import javax.jms.MessageListener;
//import javax.jms.MessageProducer;
//import javax.jms.Queue;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//import javax.jms.Topic;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.junit.Test;
//
//public class TestActiveMQ {
//	
//	//queue productor
//	@Test
//	public void testQueueProducter() throws Exception{
//		
//		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://172.27.90.226:61616");
//		Connection connection = connectionFactory.createConnection();
//		connection.start();
//		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
//		Queue queue = session.createQueue("test-queue");
//		MessageProducer producer = session.createProducer(queue);
//		TextMessage textMessage = session.createTextMessage("hello activemq111");
//		producer.send(textMessage);
//		producer.close();
//		session.close();
//		connection.close();
//		
//		
//	}
//	
//	@Test
//	public void testQueueConsumer() throws Exception{
//		
//		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://172.27.90.226:61616");
//		Connection connection = connectionFactory.createConnection();
//		connection.start();
//		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
//		Queue queue = session.createQueue("test-queue");
//		MessageConsumer consumer = session.createConsumer(queue);
//		consumer.setMessageListener(new MessageListener() {
//			//匿名内部类
//			@Override
//			public void onMessage(Message message) {
//				if(message instanceof TextMessage){
//					TextMessage textMessage=(TextMessage) message;
//					try {
//						String text = textMessage.getText();
//						System.out.println(text);
//					} catch (JMSException e) {
//						e.printStackTrace();
//					}
//				}
//				
//			}
//		});
//		//系统等待接收消息
//		/*while(true){
//			Thread.sleep(100);
//		}*/
//		//当从键盘随意敲一个字符。接收消息结束
//		System.in.read();
//		consumer.close();
//		session.close();
//		connection.close();
//		
//		
//	}
//	
//	//topic productor
//		@Test
//		public void testTopicProducter() throws Exception{
//			
//			ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://172.27.90.226:61616");
//			Connection connection = connectionFactory.createConnection();
//			connection.start();
//			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
//			Topic topic = session.createTopic("test-topic");		
//			MessageProducer producer = session.createProducer(topic);
//			TextMessage textMessage = session.createTextMessage("hello activemq topic");
//			producer.send(textMessage);
//			producer.close();
//			session.close();
//			connection.close();
//			
//			
//		}
//		
//		@Test
//		public void testTopicConsumer() throws Exception{
//			
//			ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://172.27.90.226:61616");
//			Connection connection = connectionFactory.createConnection();
//			connection.start();
//			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
//			Topic topic = session.createTopic("test-topic");
//			MessageConsumer consumer = session.createConsumer(topic);
//			consumer.setMessageListener(new MessageListener() {
//				//匿名内部类
//				@Override
//				public void onMessage(Message message) {
//					if(message instanceof TextMessage){
//						TextMessage textMessage=(TextMessage) message;
//						try {
//							String text = textMessage.getText();
//							System.out.println(text);
//						} catch (JMSException e) {
//							e.printStackTrace();
//						}
//					}
//					
//				}
//			});
//			//系统等待接收消息
//			/*while(true){
//				Thread.sleep(100);
//			}*/
//			System.out.println("消费者1.。。。。。。。。。");
//			//当从键盘随意敲一个字符。接收消息结束
//			System.in.read();
//			consumer.close();
//			session.close();
//			connection.close();
//			
//			
//		}
//	
//	
//	
//
//}
