package com.gp.正则表达式;

import java.util.regex.Pattern;



/**
 * 
 * @company keyhua
 * 
 */
public class Test {
	
	public static void main(String[] args) {
		/**
		 *  . 匹配任意 一个
		 *  * 重复上一个任意次（t*n--->tttttn）
		 *  .* 匹配任意多个（t*n--->tsaddn）
		 *  [abc] 匹配单个字符（[abc]--->a或b或c）
		 *  (abc) 全匹配（(abc)--->abc，这个和[]还有个不同之处在于，[]不能用|符号，只能用,号）
		 *  (a|bc) （(a|bc)--->a或bc）
		 *  + 重复一次或者多次
		 *  ? 重复0次或者1次
		 *  {n} 恰好重复n次
		 *  {m,n} 恰好重复m-n次
		 *  ^ “否”符号。如果用在方括号内，“^”表示不想要匹配的字符。
		 *  [0-9] = \d	（只能为0-9的数字）
		 *  [^0-9] = \D （不能为0-9的数字）
		 *  [\r\t\f\n] = \s
		 *  匹配中文:[\u4e00-\u9fa5] 
		 *  // (?i)不区分大小写
		 */
		
		/** Pattern pattern = Pattern.compile("[A-Z]",Pattern.CASE_INSENSITIVE); Pattern.CASE_INSENSITIVE : 指定大小写不敏感  */
		
		/*
		Pattern pattern = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$"); //[0-9]{9}
		System.out.println(pattern.matcher("18224106919").matches());
		*/
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5_a-zA-Z0-9_。，；【】★：、（）·]{0,100}");
		System.out.println(pattern.matcher("_4324啊啊0，。、").matches());
		/*Pattern pattern = Pattern.compile("[0-9]+[a-zA-Z]{6}"); // 
		System.out.println(pattern.matcher("18224106919").matches());*/
		
		
	}

}
