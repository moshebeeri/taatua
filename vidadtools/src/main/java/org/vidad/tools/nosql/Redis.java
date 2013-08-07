package org.vidad.tools.nosql;


import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisMonitor;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Pipeline;

/**
 * @author moshe
 *
 * read this: https://github.com/xetorthio/jedis/wiki/AdvancedUsage
 */
public class Redis {
	String host;
	String master;
	Jedis jedis;
	
	public class RedisListner extends JedisPubSub {
		

		@Override
		public void onMessage(String arg0, String arg1) {
			
		}

		@Override
		public void onPMessage(String arg0, String arg1, String arg2) {
			
		}

		@Override
		public void onPSubscribe(String arg0, int arg1) {
			
		}

		@Override
		public void onPUnsubscribe(String arg0, int arg1) {
			
		}

		@Override
		public void onSubscribe(String arg0, int arg1) {
			
		}

		@Override
		public void onUnsubscribe(String arg0, int arg1) {
			
		}
	}

	public Redis(String host) {
		this(host, host);
	}
	
	public Redis(String host, String master) {
		this.host = host;
		jedis = new Jedis(host);
		connectToMaster(master);
	}

	public void connectToMaster(String master){
		jedis.slaveof(master, 6379);
	}

	public Transaction multi(){
		return jedis.multi();		
	}

	public Pipeline pipeline(){
		return jedis.pipelined();
	}

	public void subscribe(JedisPubSub listener, String channel){
		jedis.subscribe(listener, channel);
	}

	public void publish(String channel, String message){
		jedis.publish(channel, message);
	}
	
	public void monitor(JedisMonitor monitor){
		jedis.monitor(monitor);
	}

	public void set(String key, String value){
		jedis.set(key,value);
	}
	
	public String get(String key){
		return jedis.get(key);
	}
	
	
	//############Tests
	
	
	public void testMulti(){
		Transaction t = jedis.multi();
		t.set("fool", "bar"); 
		Response<String> result1 = t.get("fool");

		t.zadd("foo", 1, "barowitch"); t.zadd("foo", 0, "barinsky"); t.zadd("foo", 0, "barikoviev");
		Response<Set<String>> sose = t.zrange("foo", 0, -1);   // get the entire sortedset
		t.exec();                                              // dont forget it

		String foolbar = result1.get();                       // use Response.get() to retrieve things from a Response
		int soseSize = sose.get().size();                      // on sose.get() you can directly call Set methods!
		System.out.println(foolbar);
		System.out.println(soseSize);
		System.out.println(sose);
	}

	public void poolTest(){
		JedisPool pool;
		JedisPoolConfig jdisConfig = new JedisPoolConfig();
		pool = new JedisPool(jdisConfig, host);
		Jedis jedis = pool.getResource();
		try {
		  /// ... do stuff here ... for example
		  jedis.set("foo", "bar");
		  String foobar = jedis.get("foo");
		  System.out.print(foobar);
		  jedis.zadd("sose", 0, "car"); 
		  jedis.zadd("sose", 0, "bike"); 
		  Set<String> sose = jedis.zrange("sose", 0, -1);
		  System.out.print(sose);
		} finally {
		  /// it's important to return the Jedis instance to the pool once you've finished using it
		  pool.returnResource(jedis);
		}
		pool.destroy();
	}

	public void test(){
		Jedis jedis = new Jedis(host);
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println(value);
	} 

	public static void main(String[] args){
		Redis r = new Redis("localhost");
		r.test();
	}
}
