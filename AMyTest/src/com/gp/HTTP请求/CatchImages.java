package com.gp.HTTP请求;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gp.HTML解析.HTMLParserHelper;



/**
 * @author 高攀
 * @上午9:58:01
 */
public class CatchImages {
	
	private static String curdesktop = System.getProperty("user.home")+"\\Desktop\\CatchImages\\";
	
	public static void main(String[] args) {
		doCatch("http://123.56.150.140:8080/OutdoorServer/webDoor/home/Home.html");
	}
	
	// 网络请求并且拿到图片链接
	public static Integer doCatch(String site){
		GetMethod method = new GetMethod(site);
		HttpClient client = new HttpClient();
		
		try {
			method.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//			method.addRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
			method.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
			method.addRequestHeader("Avail-Dictionary", "XprLfaXG");
			method.addRequestHeader("Cache-Control", "max-age=0");
			method.addRequestHeader("Connection", "keep-alive");
			method.addRequestHeader("Cookie", "");
			method.addRequestHeader("Host", "user.qzone.qq.com");
			method.addRequestHeader("If-Modified-Since", "Thu, 24 Sep 2015 02:55:30 GMT");
			method.addRequestHeader("Upgrade-Insecure-Requests", "1");
			method.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36ozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36");
			
			client.executeMethod(method);
			String htmlCode = method.getResponseBodyAsString();
			// 得到所有的img标签的链接
			Document doc = Jsoup.parse(htmlCode);
			Elements elementImg = doc.select("body img");
			for (Element element : elementImg) {
				String src = element.attr("src");
				if(src.contains("http")){ // 绝对路径就不变
					
				}else { // 否则变成绝对路径
					String rootUrl = HTMLParserHelper.getRootUrl(site); // 得到根路径
					// 加上跟路径
					src = rootUrl+src;
				}
				System.out.println(src);
				downloadImage(src);
				System.out.println("ok");
			}
			System.out.println(elementImg.size()+" result catched.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			method.abort();
			method.releaseConnection();
		}
		return 0;
	}

	// 下载图片的方法
	public static void downloadImage(String imageUrl){
		GetMethod method = new GetMethod(imageUrl);
		HttpClient client = new HttpClient();
		try {
			client.executeMethod(method);
			InputStream inputStream = method.getResponseBodyAsStream();
			
			File file = new File(curdesktop);
			if(!file.exists()){
				try {
					file.mkdir();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			byte b[] = {1};
			int size = 0;
			FileOutputStream outputStream = new FileOutputStream(new File(curdesktop+HTMLParserHelper.getImageNameAndHouzui(imageUrl)));
			while((size=inputStream.read(b))!=-1){
				outputStream.write(b, 0, size);
			}
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 释放链接
			method.abort();
			method.releaseConnection();
		}
		
	}
}
