package com.gp.HTTP请求;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class TestHttp1 {
	public static void main(String[] args) throws IllegalStateException,IOException {
		// 创建HttpClient实例
		HttpClient httpclient = new DefaultHttpClient();
		// 创建Get方法实例
		JSONObject parm = new JSONObject();
		parm.put("phonenum", "");
		parm.put("nickname", "");
		parm.put("clubname", "");
		parm.put("pagenum", 1);
		parm.put("pagesize", 20);
		HttpPost httpgets = new HttpPost("http://192.168.1.14:8080");
		// 设置参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();

		list.add(new BasicNameValuePair("inter", "doaction"));
		list.add(new BasicNameValuePair("param", parm.toString()));

		if (list.size() > 0) {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"utf-8");
			httpgets.setEntity(entity);
		}

		HttpResponse response = null;
		try {
			response = httpclient.execute(httpgets);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instreams = entity.getContent();
			String str = convertStreamToString(instreams);
			System.out.println("Do something");
			System.out.println(str);
			// Do not need the rest
			httpgets.abort();
		}
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
