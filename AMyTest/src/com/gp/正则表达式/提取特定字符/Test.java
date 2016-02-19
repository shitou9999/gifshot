package com.gp.正则表达式.提取特定字符;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	
	public static void main(String[] args) {
		
		// 提取aut 
		// {"ret":0,"cod":1000,"msg":"操作执行成功","pld":{"expertID":"FF9CCC8A-5774-4F2A-BE19-98F6336DD772","aut":"YjsdO1x8E6KjSKtQ3QN0vy"}}
		
		/**
		 * ·()里面的东西是group分组的最终概念，如果： "aut\":\"(.*)\""  代表 group(1) = (.*)
		 * ·如果： "(aut\":\")(.*)(\")" 代表 group(1) = (aut\":\")  group(2) = (.*)  group(3) = (\") 
		 * ·group(i) ，i从1开始，1就是匹配第一个
		 */
		
		String model = "{\"ret\":0,\"cod\":1000,\"msg\":\"操作执行成功\",\"pld\":{\"expertID\":\"FF9CCC8A-5774-4F2A-BE19-98F6336DD772\",\"aut\":\"YjsdO1x8E6KjSKtQ3QN0vy\"}}";
		
		Pattern pattern = Pattern.compile("aut\":\"(.*)\"");
//		Pattern pattern = Pattern.compile("(aut\":\")(.*)(\")");
//		Pattern pattern = Pattern.compile(".*(aut\":\")(.*)(\").*"); // 加上两边模糊
		
		Matcher matcher = pattern.matcher(model);
		if(matcher.find()){ // 如果有匹配的话，相当于游标，这里用了就必须重新声明了
			Integer count = matcher.groupCount();// 匹配的多少条数据
			System.out.println(count);
			for (int i = 0; i <= count; i++) { 
				System.out.println("第"+i+"条:"+matcher.group(i));
			}
		}
		Matcher matcher1 = pattern.matcher(model);
		if(matcher1.find()){
			System.out.println("那么aut="+matcher1.group(1));
		}
		System.out.println(matcher.matches());
	}

}
