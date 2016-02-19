package com.gp.IP代理;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * @author 高攀
 * @下午5:54:32 设置网络请求的ip代理，以免高频访问网站被禁
 */
public class IPProviderDemo {

	public static void main(String[] args) {
		System.setProperty("http.maxRedirects", "50");
		System.getProperties().setProperty("proxySet", "true");
		
		// 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
		String ip = "182.140.132.107"; // http://www.xicidaili.com/ 获取免费代理ip的地址
		System.getProperties().setProperty("http.proxyHost", ip);
		System.getProperties().setProperty("http.proxyPort", "8888");
		
		Properties  properties = System.getProperties();
		Set<Entry<Object, Object>> set = properties.entrySet();
		for (Entry<Object, Object> entry : set) {
			System.out.println(entry.getKey()+" -- "+entry.getValue());
		}

		// 确定代理是否设置成功
		System.out.println(getHtml("http://www.ip138.com/ip2city.asp"));
		
		
	}

	private static String getHtml(String address) {
		StringBuffer html = new StringBuffer();
		String result = null;
		try {
			URL url = new URL(address);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)");
			BufferedInputStream in = new BufferedInputStream(
					conn.getInputStream());

			try {
				String inputLine;
				byte[] buf = new byte[4096];
				int bytesRead = 0;
				while (bytesRead >= 0) {
					inputLine = new String(buf, 0, bytesRead, "ISO-8859-1");
					html.append(inputLine);
					bytesRead = in.read(buf);
					inputLine = null;
				}
				buf = null;
			} finally {
				in.close();
				conn = null;
				url = null;
			}
			result = new String(html.toString().trim().getBytes("ISO-8859-1"),
					"gb2312").toLowerCase();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			html = null;
		}
		return result;
	}

}
