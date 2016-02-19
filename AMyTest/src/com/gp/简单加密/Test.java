package com.gp.简单加密;
/**
 * @author 高攀
 * @下午 6:09:56
 */
public class Test {
	
	public static void main(String[] args) {
		String key = "gongpan";
		String source = "xiba啊";
		char[] cha = source.toCharArray();
		for (char c : cha) {
			System.out.println(c^1^1);
		}
	}

}
