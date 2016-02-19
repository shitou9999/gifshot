package com.gp.HTTP请求;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class TestHttp {

	public static void main(String[] args) {
		try {
			// 要想struts2的表单值天器自动填充就必须使用伪URL传参不管是使用get还是POST
			// ?path=c:/test.xml&test=2012
			String spec = "http://localhost:8080/OutdoorServer/pc/getuserlist";
			URL url = new URL(spec);
			System.out.println(url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("content-type", "text/html");

			conn.connect();// 握手
			OutputStream os = conn.getOutputStream();// 拿到输出流
			// os.write("?path=c:/test.xml&test=2012".getBytes("utf-8"));
			PrintWriter out = new PrintWriter(os);
			out.print("?inter=doaction&param=");
			JSONObject parm = new JSONObject();
			parm.put("phonenum", "");
			parm.put("nickname", "");
			parm.put("clubname", "");
			parm.put("pagenum", 1);
			parm.put("pagesize", 20);
			out.print(parm.toString());
			out.flush();
			os.flush();
			out.close();
			os.close();

			InputStream is = conn.getInputStream();// 拿到输入流
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer temp = new StringBuffer();
			String line = br.readLine();
			while (line != null) {
				// httpResponser.contentCollection.add(line);
				temp.append(line).append("\r\n");
				line = br.readLine();
			}
			System.out.println(temp);
			br.close();
			isr.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
