package com.taotao.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {
	
	@Test
	public void TestJedis() throws Exception{
		
		Jedis jedis=new Jedis("192.168.25.128", 6379);
		jedis.set("jedis-key", "123");
		String result = jedis.get("jedis-key");
		System.out.println(result);
		jedis.close();
		
	}
	
	@Test
	public void TestJedisPool() throws Exception{
		
		JedisPool jedisPool=new JedisPool("192.168.25.128", 6379);
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get("jedis-key");
		System.out.println(result);
		jedis.close();
		jedisPool.close();
	}
	
	/*@Test
	public void TestJedisCluster() throws Exception{
		
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("172.27.90.226", 7011));
		nodes.add(new HostAndPort("172.27.90.226", 7012));
		nodes.add(new HostAndPort("172.27.90.226", 7013));
		nodes.add(new HostAndPort("172.27.90.226", 7014));
		nodes.add(new HostAndPort("172.27.90.226", 7015));
		nodes.add(new HostAndPort("172.27.90.226", 7016));
		JedisCluster jedisCluster=new JedisCluster(nodes);
		jedisCluster.set("cluster-test", "hello jedis cluster");
		String string = jedisCluster.get("cluster-test");
		System.out.println(string);
		
		jedisCluster.close();
	}*/

}
