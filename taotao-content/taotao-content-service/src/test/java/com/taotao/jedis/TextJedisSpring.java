package com.taotao.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TextJedisSpring {
	@Test
	public void textJedisClientPool() throws Exception{
		
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		jedisClient.set("jedisClient", "nihao");
		String string = jedisClient.get("jedisClient");
		System.out.println(string);
		
	}

}
