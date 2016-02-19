package com.gp.一个简单的抽奖算法;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



/**
 * @author 高攀
 * 奖池概率算法模块
 * 根据设置的每一个奖项的概率
 * 会在数组里面生成对应百分比的该奖项个数
 */
public class test3 {

	public static void main(String[] args) {
		
		HashMap<String, Double> lottoShootRates = new HashMap<String, Double>();
		lottoShootRates.put("id1", 0.1);
		lottoShootRates.put("id2", 0.05);// 如果数组是100大小，则id2会出现5次
		lottoShootRates.put("id3", 0.3);
		lottoShootRates.put("id4", 0.5);
		lottoShootRates.put("id5", 0.05);
		
		// 计算并生成抽奖的奖池数组
		// 首先找到中奖概率最低的奖项，并以此概率的小数点位数来确定抽奖的奖池的范围
		Double minShootRate = 1.0;
		System.out.println("计算并生成抽奖的奖池数组");
		Iterator<Map.Entry<String, Double>> iter1 = lottoShootRates.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry<String, Double> entry = (Map.Entry<String, Double>) iter1.next();
			Double value = entry.getValue();
			minShootRate = Math.min(minShootRate, value);
		}
		
		// 确定数组大小 lottoShootRange = 10
		Integer lottoShootRange = (int)(Math.pow(10, getDecimalPrecision(minShootRate)));
		String lottoShootArray[] = new String[lottoShootRange]; 
		Integer i = 0;
		
		// 确定概率
		Iterator<Map.Entry<String, Double>> iter2 = lottoShootRates.entrySet().iterator();
		while (iter2.hasNext()) {
			Map.Entry<String, Double> entry = (Map.Entry<String, Double>) iter2.next();
			String key = entry.getKey();
			Double value = entry.getValue();
			Integer lottoShootSubRange =(int)(value*lottoShootRange);
			//System.out.println("lottoShootSubRange:" + lottoShootSubRange);
			// 概率越大，排在数组越后
			for (int j=0; (j<lottoShootSubRange) && (i<lottoShootRange); ++i, ++j) {
				lottoShootArray[i]=key;
			}
		}
		for (int j = 0; j < lottoShootArray.length; j++) {
			System.out.print(lottoShootArray[j]+" ");
		}
		System.out.println(lottoShootArray.length);
		
		System.out.println("------------开始抽奖------------");
		System.out.println("抽到了："+shootLotto(lottoShootArray));
	}
	
	/**
	 * 获取指定浮点数的小数位数
	 */
	private static Integer getDecimalPrecision(Double value) {
		Integer number = 0;
		String str = value.toString();
		Integer dotIndex = str.indexOf('.');
		if (-1 != dotIndex) {
			number = str.substring(dotIndex+1, str.length()).length();
		}
		return number;
	}
	/**
	 * 抽奖
	 */
	private static String shootLotto(String[] array){
		String shootCode = null;
		if (null != array) {
			Integer lottoShootIndexRange = array.length;
			// 在指定范围生成一个随机数作为奖池数组的索引
			SecureRandom random = new SecureRandom();
			Integer lottoShootIndex = random.nextInt(lottoShootIndexRange);
			shootCode = array[lottoShootIndex];
			// 检查剩余奖项数量，如果剩余奖项为0，则抽到该奖项被当做没有中奖处理
			// 此处没有做线程保护，注意多线程时可能存在数据不一致的可能
			if (null != shootCode && !"".equals(shootCode)) {
//				Integer remainCount = RedisHelper.getInstance().getRemainAwardCount(shootCode);
//				if (remainCount<=0) { // 剩余奖品数
//					shootCode = null;
//				}
			}
			// 剩余奖项数量的更新在，唉后续的操作成功后再执行，一旦后续出现任何错误或异常，都认为该次抽奖没有中奖，不更新剩余奖项数据
		}
		return shootCode;
	}
}
