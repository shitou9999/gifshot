package com.gp.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 高攀
 * @上午11:06:21
 * redis 帮助类
 */
public class RedisHelper {
	
	private static RedisHelper helper = null;
	private static JedisPool pool = null;
	
	private RedisHelper(){
		init();
	}
	private void init(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(100);
		config.setMaxTotal(10000);
		config.setMaxWaitMillis(1000);
		config.setTestOnBorrow(true);
		pool = new JedisPool(config,"127.0.0.1",6379,1000,"123456");
	}
	
	public synchronized static RedisHelper getInstance(){
		if(null == helper){
			helper = new RedisHelper();
		}
		return helper;
	}
	public Jedis getResource(){
		return pool.getResource();
	}
}
