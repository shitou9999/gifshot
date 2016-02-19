package com.gp.redis;

import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * @author 高攀
 * @下午 2:57:19
 */
public class RedisConnection {
	
	

	public static void main(String[] args) {
		
		Jedis jedis = new Jedis("182.92.150.80");
		jedis.auth("123456");
		/*List<String> list = jedis.lrange("test_action_count", 0, 1);
//		jedis.del("test_action_count");
		if(null!=list && list.size()>0){
			Integer count = Integer.parseInt(list.get(0));
			count++;
			jedis.del("test_action_count");
			jedis.lpush("test_action_count", count+"");
		}else {
			jedis.lpush("test_action_count", 1+"");
		}
		List<String> list1 = jedis.lrange("test_action_count", 0, 1);
		jedis.close();
		System.out.println(list1);*/
		System.out.println(jedis.keys("*"));
//		jedis.del("test_action_count_hash");
		/*if(null!=countRedis && !"".equals(countRedis)){
			Integer count = Integer.parseInt(countRedis);
			count++;
			jedis.del("test_action_count_hash");
			jedis.hset("test_action_count_hash", "count",count+"");
		}else {
			jedis.hset("test_action_count_hash", "count",1+"");
		}
		String countRedis1 = jedis.hget("test_action_count_hash", "count");
		jedis.close();
		System.out.println(countRedis1);*/
		
		/*jedis.auth("123456");
		
		// 获得list列表
		List<String> string = jedis.lrange("myList", 0, 100);
		for (String string2 : string) {
			System.out.println(string2);
		}
		System.out.println("如\t\r\n");*/
		/*// 在v5[v6]上面插入一条数据
		String index = jedis.lindex("myList", 3);
		System.out.println(index+"==");
		jedis.linsert("myList", BinaryClient.LIST_POSITION.BEFORE, index, "v55");
		 */
		/*// 移除指定值的list中的元素
		// count>0 从表头向表尾，移除count个和value相等的元素
		// count=0 移除所有和vaule相等的元素
		// count<0 从表尾到表头，移除count个和vaule相等的元素
		System.out.println(jedis.lrem("myList", 0, "v3"));*/
		/*RedisHelper rs = RedisHelper.getInstance();
		Jedis jedis = rs.getResource();
		List<String> list = jedis.lrange("myList", 0, 100);
		for (String string : list) {
			System.out.println(string);
		}*/
		
		// 分类集合（Sorted Sets）
		/*long l = jedis.zcount("myZSort", 10, 30);
		System.out.println(l);
		System.out.println(jedis.zcard("myZSort"));*/
		
//		jedis.lpush("advert", "asdasd1");
//		jedis.lpush("advert", "asdasd2");
		/*List<String> string = jedis.lrange("advert", 0, 100);
		for (String string2 : string) {
			System.out.println(string2);
		}
//		jedis.lrem("advert", 0, "asdasd1");
		System.out.println("===");
		jedis.setnx("gp.lock", "lock");*/
		/*Classrun classr = new Classrun();
		Integer count = 0;
		for (int i = 0; i < 10; i++) {
			Long lock = jedis.setnx("gp.lock", "lock");
//			System.out.println("上一次lock:"+lock);
			if(null!=lock && lock == 1){
				new Thread(classr).start();
				jedis.del("gp.lock");
			} else {
				System.out.println("=====================");
				continue;
			}
			count = i;
		}
		System.out.println("\n\n\n\n\n\n-----------"+count+"-----------");*/
//		jedis.auth("123456");
//		jedis.hset("myHset", "a", "1");
	}
	
	
	public void fun(Integer sum){
		for (int i = 0; i < 100; i++) {
			System.out.print(sum+" ");
			sum--;
		}
		System.out.println();
	}
	
}
class Classrun implements Runnable{
	public static Integer count = 100;
	public static Integer sum = 100; // 资源
	private static RedisConnection connection = new RedisConnection();
	
	@Override
	public void run() {
		connection.fun(sum);
	}
	
}

