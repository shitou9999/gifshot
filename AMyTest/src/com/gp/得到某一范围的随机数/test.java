package com.gp.得到某一范围的随机数;

import java.util.Random;

/**
 * @author 高攀
 * @下午4:59:17
 */
public class test {

	public static void main(String[] args) {
		
		getRandomString(10);
		
	}

	/**
	 * 获取指定长度的随机字符串
	 * @param length
	 * @return 结果
	 */
	public static String getRandomString(Integer length){
		String base = "";
		// A-Z : 65-90 | a-z : 97-122 
		// 得到A-Z的数
		char A = (char) getRangeNum(65, 90);
		// 得到a-z的数
		char a = (char) getRangeNum(97, 122);
		for (int i = 0; i < length; i++) {
			System.out.print((char) getRangeNum(65, 90));
			System.out.print((char) getRangeNum(97, 122));
		}
		return "";
	}
	
	/**
	 * 生成在[min,max]之间的随机整数
	 * @param min 最小值
	 * @param max 最大值
	 * @return 结果
	 */
	public static int getRangeNum(int min,int max){
		Random random = new Random();
    	int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}
}
