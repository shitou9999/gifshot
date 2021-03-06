package com.gp.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.sun.jmx.snmp.Timestamp;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * @author 高攀
 * @下午4:59:17
 */
public class test2 {

	public static void main(String[] args) {
//		SubmitPost("http://localhost:8080/OutdoorServer/xmlparse", "岗仁波齐转山.kml", null, "C:\\Users\\liupei\\Desktop\\");
//		SubmitPost("http://123.56.150.140:8080/OutdoorServer/xmlparse", "岗仁波齐转山.kml", null, "C:\\Users\\liupei\\Desktop\\");
//		SubmitPost("http://localhost:1024", "vip加福豆.txt", null, "C:\\Users\\liupei\\Desktop");
		// 1438860460000
		String time = "1438860460000";
		long s = Long.parseLong(time);
		Timestamp timestamp = new Timestamp(s);
		System.out.println(timestamp);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(timestamp.getDate()));
		
		// 申请日期	2015-8-6 19:27:40
		// 成为商家日期2015-12-23 10:43:27
		
		String timeStr = "1438860460000";
		long ss = Long.parseLong(timeStr);
		java.sql.Timestamp timestamp2 = new java.sql.Timestamp(ss);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(timestamp2.getDate()));
		System.out.println(timestamp2);
	}

	// file1与file2在同一个文件夹下 filepath是该文件夹指定的路径
	public static void SubmitPost(String url, String filename1, String filename2, String filepath) {
		
		HttpClient httpclient = new DefaultHttpClient();

		try {

			HttpPost httppost = new HttpPost(url);
			FileBody bin = new FileBody(new File(filepath + File.separator + filename1));
//			FileBody bin2 = new FileBody(new File(filepath + File.separator + filename2));

			StringBody comment = new StringBody(filename1);

			MultipartEntity reqEntity = new MultipartEntity();

			reqEntity.addPart("file1", bin);// file1为请求后台的File upload;属性
//			reqEntity.addPart("file2", bin2);// file2为请求后台的File upload;属性
			reqEntity.addPart("filename1", comment);// filename1为请求后台的普通参数
			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);

			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {
				System.out.println("---");
				HttpEntity resEntity = response.getEntity();
				InputStream in = resEntity.getContent();
				StringBuilder builder = new StringBuilder("");
				byte[] b = new byte[in.available()];
				while(in.read(b)!=-1){
//					System.out.println();
					builder.append(new String(b,"utf-8"));
				}
				EntityUtils.consume(resEntity);
				System.out.println(builder.toString());
				in.close();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {

			}
		}
	}

}