package com.taotao.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener {

	//MessageListener的实现方法
	@Override
	public void onMessage(Message message) {
		
		try {
			TextMessage textMessage=(TextMessage) message;
			String text = textMessage.getText();
			System.out.println(text);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
