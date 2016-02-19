package com.gp.HTTP请求;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 
 * @company keyhua
 * @author gpa
 */
public class test {
	public static void main(String[] args) {
		PostMethod method = new PostMethod("http://180.186.38.200/rest/n/feed/list?type=7&lat=40.049317&lon=116.296499&ver=4.34&ud=0&sys=ANDROID_4.4.2&c=GENERIC&net=WIFI&did=ANDROID_4ee72f6c3c98b87d&mod=bignox%28NoxW%29&app=0&language=zh-cn&country_code=CN HTTP/1.1&os=android&client_key=3c2cd3f3&last=31&count=20&token=&page=1&pcursor=&pv=false&mtype=2&type=7&sig=0546013b9ade238f765ba0d4c3dab326");
		HttpClient client = new HttpClient();

		try {
			/**
			 *  Accept-Language: zh-cn
				User-Agent: kwai-android
				Host: 180.186.38.200
				Connection: Keep-Alive
				Accept-Encoding: gzip
				Content-Type: application/x-www-form-urlencoded
				Content-Length: 131
				
				os=android&client_key=3c2cd3f3&last=31&count=20&token=&page=1&pcursor=&pv=false&mtype=2&type=7&sig=0546013b9ade238f765ba0d4c3dab326HTTP/1.1 200 OK
				Date: Mon, 12 Oct 2015 13:04:28 GMT
				Content-Type: application/json;charset=UTF-8
				Transfer-Encoding: chunked
				Connection: keep-alive
				Content-Encoding: gzip
				
				------------------------
				HTTP/1.1 200 OK
				Date: Tue, 13 Oct 2015 06:37:36 GMT
				Content-Type: application/json;charset=UTF-8
				Transfer-Encoding: chunked
				Connection: keep-alive
				Content-Encoding: gzip
			 */
			method.addRequestHeader("Accept-Language", "zh-cn");
			method.addRequestHeader("User-Agent", "kwai-android");
			method.addRequestHeader("Host", "180.186.38.200");
			method.addRequestHeader("Connection", "Keep-alive");
			method.addRequestHeader("Accept-Encoding", "gzip");
			method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			method.addRequestHeader("Content-Length","131");
			client.executeMethod(method);
			String htmlCode = method.getResponseBodyAsString();
			System.out.println(htmlCode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.abort();
			method.releaseConnection();
		}
	}
	
	public void kuaishouCode(){/*
		 	str12 = "true";
	        arrayOfObject4[7] = str12;
	        arrayOfObject4[8] = paramString1;
	        arrayOfObject4[9] = str1;
	        if (!paramBoolean2)
	          break label603;
	        str13 = "true";
	        arrayOfObject4[10] = str13;
	        arrayOfObject4[11] = "3ef750b22f3e";
	        str4 = String.format("caption=%sclient_key=%sinterval=%dlat=%.6flon=%.6fos=androidpreid=%sthird_platform_tokens=%sto_gifshow=%stoken=%sver=%swait=%s%s", arrayOfObject4);
	        localHashMap.put("token", paramString1);
	        localHashMap.put("os", "android");
	        localHashMap.put("caption", paramString2);
	        localHashMap.put("interval", i);
	        Object[] arrayOfObject2 = new Object[1];
	        arrayOfObject2[0] = Double.valueOf(paramDouble1);
	        localHashMap.put("lat", String.format("%.6f", arrayOfObject2));
	        Object[] arrayOfObject3 = new Object[1];
	        arrayOfObject3[0] = Double.valueOf(paramDouble2);
	        localHashMap.put("lon", String.format("%.6f", arrayOfObject3));
	        localHashMap.put("preid", paramString3);
	        localHashMap.put("client_key", "5aec8372");
	        if (!paramBoolean2)
	          break label743;
	        str5 = "true";
	        localHashMap.put("wait", str5);
	        if (!paramBoolean1)
	          break label751;
	        str6 = "true";
	        localHashMap.put("to_gifshow", str6);
	        localHashMap.put("ver", str1);
	        localHashMap.put("sig", ae.a(str4));
	        boolean bool = i.b(paramFile.getName());
	        StringBuilder localStringBuilder = new StringBuilder("http://api.gifshow.com/rest/");
	        if (!bool)
	          break label759;
	        str7 = "photo/upload3";
	        String str8 = str7;
	        if (!bool)
	          break label767;
	        str9 = "file";
	        locala = new com.onesmiletech.util.d.a(str8, null, localHashMap, str9, paramFile);
	        locala.a(paramSimpleProgressListener);
	        if (!paramBoolean2)
	          break label775;
	*/}

}
