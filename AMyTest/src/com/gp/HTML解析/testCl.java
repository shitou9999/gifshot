package com.gp.HTML解析;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class testCl {

	private static String host = "cl.yucl.pw";
	public static void main(String[] args) {
		
		parseClAndSearch(".*[图|发码|福利].*",900);
//		String keyWord = ".*[图|发码|福利].*";
//		String title = "aaa码数据库黑色的果农";
//		System.out.println(Pattern.compile(keyWord).matcher(title).matches());
	}
	
	/**
	 * 查找所有信息
	 * 我决定把所有标题和链接存放在txt文件里面
	 */
	public static Map<String, String> parseClPage(){
		String html = "http://"+host+"/thread0806.php";
		String currentuserdesktop = System.getProperty("user.home")+"\\Desktop"; 
		Map<String, String> resultMap = new TreeMap<String, String>(); // 结果-链接
		Document doc = null;
		try {
			for (int i = 0; i < 199; i++) { // 设置扫描的页数范围
				StringBuffer htmlCode = new StringBuffer("");
				HttpMethod httpMethod = new GetMethod("http://"+host+"/thread0806.php?fid=7&search=&page="+(i+1));
				HttpClient client = new HttpClient();
				httpMethod.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				httpMethod.addRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
				httpMethod.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
				httpMethod.addRequestHeader("Referer", "http://"+host+"/thread0806.php?fid=7");
				httpMethod.addRequestHeader("HTTPS", "1");
				httpMethod.addRequestHeader("Connection", "keep-alive");
//				httpMethod.addRequestHeader("Cookie", "hasShowWeiyun1126755965=1; __Q_w_s__QZN_TodoMsgCnt=1; __Q_w_s_hat_seed=1; __Q_w_s__appDataSeed=1; pgv_pvid=5426857907; pgv_info=ssid=s2065473618; pt_clientip=4f7dabdd24e1ac88; pt_serverip=1c070a9319e3179b; pt2gguin=o1126755965; uin=o1126755965; skey=@iI93viCAw; ptisp=ctc; RK=3pF+AiedN0; qzone_check=1126755965_1443074486; ptcz=17bc4a07668399dab83926ceb87b031c88335cc00a053e484aa23674b10ec9ca; Loading=Yes; qzspeedup=sdch; p_skey=Qj4LwJCO6hlETz5lZfYyuOBMX3HTV4oUzJAGVGe6rHc_; pt4_token=-Uyn4aAaN5w9uPVsVMq4oA__; qz_screen=1920x1080; QZ_FE_WEBP_SUPPORT=1; cpu_performance_v8=3");
				httpMethod.addRequestHeader("Host", host);
				httpMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36ozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36");
				client.setTimeout(3000);
				client.executeMethod(httpMethod);
				InputStream inputStream = httpMethod.getResponseBodyAsStream();
				GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
				InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream,Charset.forName("gb2312"));
				BufferedReader bin21 = new BufferedReader(inputStreamReader);
				while(bin21.readLine()!=null){
					String line = bin21.readLine();
					htmlCode.append(line);
				}
				doc = Jsoup.parse(htmlCode.toString());
				Elements elementsTr = doc.select("table tr");
				for (Element element : elementsTr) {
					String title = element.select("td").eq(1).select("h3 a").text();
					if(null!=title && !"".equals(title)){
//						System.out.println(title);
						String link = "http://"+host+"/"+element.select("td").eq(1).select("h3 a").attr("href");
//						resultMap.put(title, link);
						// 文件流
						writefiletotxt((new FileWriter(currentuserdesktop+"\\查找结果.txt",true)),("标题："+title+"\t链接："+link+"\r\n"));
					}
				}
				// 释放链接
				httpMethod.abort();
				httpMethod.releaseConnection();
//				resultMap.remove("");
			}
			System.out.println("done--");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	public static void writefiletotxt(FileWriter fw,String result){
		try {
			fw.write(result);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 搜索关键词，用逗号分隔多条件
	 */
	public static Map<String, String> parseClAndSearch(String keyWord,Integer pageMax){
		Map<String, String> resultMap = new TreeMap<String, String>(); // 结果-链接
		Pattern pattern = Pattern.compile(keyWord);
		try {
			for (int i = 0; i < pageMax; i++) { // 设置扫描的页数范围
				StringBuffer htmlCode = new StringBuffer("");
				HttpMethod httpMethod = new PostMethod("http://"+host+"/thread0806.php?fid=7&search=&page="+(i+1));
				HttpClient client = new HttpClient();
				
				httpMethod.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				httpMethod.addRequestHeader("CF-RAY", "2473b49126852270-LAX");
				httpMethod.addRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
				httpMethod.addRequestHeader("Referer", "http://"+host+"/thread0806.php?fid=7");
				httpMethod.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
				httpMethod.addRequestHeader("Upgrade-Insecure-Requests", "1");
				httpMethod.addRequestHeader("Connection", "keep-alive");
				httpMethod.addRequestHeader("Host", host);
				httpMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36");
				System.out.println("1");
				client.executeMethod(httpMethod);
				System.out.println("2.01");
				InputStream inputStream = httpMethod.getResponseBodyAsStream();
				System.out.println("2.02");
				GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
				System.out.println("2.00");
				InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream,Charset.forName("gb2312"));
				System.out.println("2.0");
				BufferedReader bin21 = new BufferedReader(inputStreamReader);
				System.out.println("2");
				while(bin21.readLine()!=null){
					String line = bin21.readLine();
					htmlCode.append(line);
				}
				System.out.println("3");
				Document doc = Jsoup.parse(htmlCode.toString());
				Elements elementsTr = doc.select("table tr");
				for (Element element : elementsTr) {
					String title = element.select("td").eq(1).select("h3 a").text();
					if(null!=title && !"".equals(title)){
						String link = "http://"+host+"/"+element.select("td").eq(1).select("h3 a").attr("href");
//						System.out.println(title);
						if(pattern.matcher(title).matches()){
							System.out.println("\n"+title+"："+link);
							resultMap.put(title, link);
						}
						/*if(keyWord.contains(",")){ // 多条件
							String[] key = keyWord.split(",");
							for (String string : key) {
								if(null!=string && !"".equals(string)){
									if(title.contains(string)){
										System.out.println("\n"+title+"："+link);
										resultMap.put(title, link);
									}
								}
							}
						}else {
							if(title.contains(keyWord)){
								System.out.println("\n"+title+"："+link);
								resultMap.put(title, link);
							}
						}*/
						
					}
				}
				// 释放链接
				httpMethod.abort();
				httpMethod.releaseConnection();
				
				resultMap.remove("");
				System.out.print("第"+(i+1)+"页... ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	// 参考网上的
	public static String getHtmlContent(String htmlurl, String charset) throws IOException {
		StringBuffer sb = new StringBuffer();
		String acceptEncoding = "";
		/* 1.生成 HttpClinet 对象并设置参数 */
		HttpClient httpClient = new HttpClient();
		// 设置 Http 连接超时 5s
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		GetMethod method = new GetMethod(htmlurl);
		method.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		method.addRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
		method.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		method.addRequestHeader("Referer", "http://cl.clvv.biz/thread0806.php?fid=7");
		method.addRequestHeader("HTTPS", "1");
		method.addRequestHeader("Connection", "keep-alive");
		method.addRequestHeader("Host", "cl.clvv.biz");
		method.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36ozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36");
		// 设置 get 请求超时 5s
		method.getParams().getDoubleParameter(HttpMethodParams.SO_TIMEOUT, 10000);
		// 设置请求重试处理
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		int statusCode;
		try {
			statusCode = httpClient.executeMethod(method);
			// 判断访问的状态码
			if (statusCode != HttpStatus.SC_OK) {
				return sb.toString();
			} else {
				if (method.getResponseHeader("Content-Encoding") != null)
					acceptEncoding = method.getResponseHeader("Content-Encoding").getValue();
				if (acceptEncoding.toLowerCase().indexOf("gzip") > -1) {
					// 建立gzip解压工作流
					InputStream is;
					is = method.getResponseBodyAsStream();
					GZIPInputStream gzin = new GZIPInputStream(is);
					InputStreamReader isr = new InputStreamReader(gzin, charset); // 设置读取流的编码格式，自定义编码
					java.io.BufferedReader br = new java.io.BufferedReader(isr);
					String tempbf;
					while ((tempbf = br.readLine()) != null) {
						sb.append(tempbf);
						sb.append("\r\n");
					}
					isr.close();
					gzin.close();
				} else {
					InputStreamReader isr;
					isr = new InputStreamReader(method.getResponseBodyAsStream(), charset);
					java.io.BufferedReader br = new java.io.BufferedReader(isr);
					String tempbf;
					while ((tempbf = br.readLine()) != null) {
						sb.append(tempbf);
						sb.append("\r\n");
					}
					isr.close();
				}
			}
		} catch (HttpException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		method.abort();
		method.releaseConnection();
		return sb.toString();
	}


}

