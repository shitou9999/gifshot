package com.gp.sql注册正则;

import java.util.regex.Pattern;

/**
 * @author 高攀
 * @下午3:43:34
 */
public class SqlSecurity {

	public static void main(String[] args) {
		
		String sql = "asajdin truncseletae'dopsertbha；isb ";
//		boolean result = SqlSecurity.sql_inj(sql);
//		System.out.println("-----"+result+"-----");
		
		boolean result = SqlSecurity.sql_Reg(sql);
		System.out.println("-----"+result+"-----");

	}

	/**
	 * 
	 * @param str
	 * @return true:验证通过；false:验证失败，包含sql敏感内容
	 */
	public static boolean sql_inj(String str) {
		String inj_str = "`|'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,|drop";
		String inj_stra[] = inj_str.split("\\|");
		for (int i = 0; i < inj_stra.length; i++) {
			System.out.println(inj_stra[i]);
			if (str.indexOf(inj_stra[i]) >= 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param string
	 * @return true:验证通过；false:验证失败，包含sql敏感内容
	 */
	public static boolean sql_Reg(String string) {
		// (?i)不区分大小写
		String inj_str = "(?i).*(`|'|and|exec|insert|select|delete|update|count|\\*|%|chr|mid|master|truncate|char|declare|;|or|\\+|,|drop|table).*";
		return !Pattern.compile(inj_str).matcher(string).matches();
	}
}
