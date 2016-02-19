package com.gp.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import com.gp.json.JSONArray;

/**
 * @author 高攀
 * @下午4:48:45
 */

public class Snippet {
	public static void main(String[] args) {

		
		Integer index = 0;
		Integer count = 10;
		
		// http://localhost:8080/wot.kongzhong.com/film.security?index=0&count=1
		String url = "http://192.168.1.13:8080/wot.kongzhong.com/film.security"
				+ "?index=" + index + "&count=" + count;
		HttpMethod httpMethod = new GetMethod(url);
		HttpClient client = new HttpClient();
		try {
			
			client.executeMethod(httpMethod);
			String data = httpMethod.getResponseBodyAsString();
			JSONArray array = new JSONArray(data);
			System.out.println(array.toString());
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
