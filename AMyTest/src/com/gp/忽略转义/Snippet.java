package com.gp.忽略转义;
// 导包：commons-lang-2.6.jar
import org.apache.commons.lang.StringEscapeUtils;
import com.gp.json.JSONObject;
/**
 * @author 高攀
 * @下午5:13:15
 */

public class Snippet {
	public static void main(String[] args) {
		try {
			JSONObject jsonOut = new JSONObject();
			jsonOut.put("json", "{\"a\":1,\"b\":\"xiba\"}");
			System.out.println(jsonOut.toString());
			String s2 = StringEscapeUtils.unescapeJava(jsonOut.toString());
			System.out.println(s2);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
