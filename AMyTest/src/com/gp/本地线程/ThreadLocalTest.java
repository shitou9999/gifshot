package com.gp.本地线程;
/**
 * 
 * @author 高攀
 * 
 *
 */ 
public class ThreadLocalTest {

	public static void main(String[] args) {
		ThreadLocal<String> string = new ThreadLocal<String>();
		string.set("jiba");
		System.out.println(string.get());
	}
}
