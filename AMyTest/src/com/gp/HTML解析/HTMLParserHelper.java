package com.gp.HTML解析;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author 高攀
 * @下午5:02:37
 * 解析html的类，提供基本的方法
 */
public class HTMLParserHelper {
	
	public static void main(String[] args) {
		
		try {
			System.out.println(getFreeProviderList("sichuan", "2014-09-20", null).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param website 网址
	 * @param reqParam	可选的请求参数，没有参数就new一个map。
	 * @return
	 * @throws Exception 打开网址有误！
	 */
	public static String getHTMLCode(String website,Map<String,Object> reqParam) throws IOException{
			StringBuffer hTMLCode = new StringBuffer("");
			StringBuffer reqParamTemp = new StringBuffer("");
			if(reqParam.size()>0){
				Set<Entry<String, Object>> set = reqParam.entrySet();
				int i = 0;
				for (Entry<String, Object> entry : set) {
					String preParam = i==0?"?":"&";
					reqParamTemp.append(preParam+entry.getKey()+"="+entry.getValue());
					i++;
				}
				i = 0;
				// 把参数拼接到请求网址
				website+=reqParamTemp.toString();
			}
//			System.out.println(website);
			/*try {
			 *  // 编码请求参数
				name = URLEncoder.encode(name, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}*/
			try {
				URL url = new URL(website);
				URLConnection  con = url.openConnection();
				// 设置通用的请求属性，必选
				con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36");
				con.setRequestProperty("Cookie", "PANWEB=1; bdshare_firstime=1425978522016; Hm_lvt_b181fb73f90936ebd334d457c848c8b5=1436958786; PSTM=1447299706; BIDUPSID=4EA39B276960F378D5D35E912973762F; BAIDUID=C10D54530EA217F810DB4C823C711347:FG=1; BDUSS=BUfnJseEJ3a05CM0F6OVgyQjduUjJWTVRnVFJkVVlZc0NhN0xndmppYkI5SEJXQVFBQUFBJCQAAAAAAAAAAAEAAAA40vAguajFyrChAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMFnSVbBZ0lWYn; MCITY=-%3A; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1450749510; BDCLND=cr8TRtE3QEYUnKKdcXbe3MHikampaKfQxBzsOzCa3v4%3D; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; H_PS_PSSID=17746_1438_18534_12824_10213_18501_18545_17564_17001_17072_15645_12077_13614_18016; PANPSC=8107813015595255963%3AkpNhOLMYOxQQGxxMMWyZten90oj%2BY%2FIsVimLqX5INDoMWDxEDiLgePOFmgcgWNJZeBeh%2BtYRIjiJDuBmTmz39AS36falN2FzTHC3Jhu5gktGHywuTt2tpT%2FTB73TfUiYqBJ6OCtNcTomloa5sr%2BuGUHHfaoqyixBZd6D3jSSO21jp%2B3P05c24T4wgdQ%2F1cWMw5CRGLLjXYs8g1PcRcrciFklnS5G%2BZJP; Hm_lvt_773fea2ac036979ebb5fcc768d8beb67=1450692140,1450749490,1450766041,1450943186; Hm_lpvt_773fea2ac036979ebb5fcc768d8beb67=1450943191; Hm_lvt_adf736c22cd6bcc36a1d27e5af30949e=1450692140,1450749490,1450766041,1450943186; Hm_lpvt_adf736c22cd6bcc36a1d27e5af30949e=1450943191");
				con.setRequestProperty("Connection", "keep-alive");
				con.connect();
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String lines = null;
				while ((lines = reader.readLine()) != null) {
					hTMLCode.append(lines+"\n");
				}
			} catch (Exception e) {
				throw new IOException(e.getMessage());
			}
			return hTMLCode.toString();
	}
	
	/**
	 * 用JQuery方式获得指定节点指定属性值
	 * @param htmlCode html源代码
	 * @param select
	 * @param attrName 属性值，如（title、href）；text是获取节点之间的文本
	 */
	public static List<String> getJQueryData(String htmlCode,String select,String attrName){
		Document doc = Jsoup.parse(htmlCode);
		List<String> result = new ArrayList<String>();
		Elements elements = doc.select(select);
		if("text".equalsIgnoreCase(attrName)){
			for (Element element : elements) {
				result.add(element.text());
			}
		} else {
			for (Element element : elements) {
				result.add(element.attr(attrName));
			}
		}
		return result;
	}
	
	/**
	 * 获得免费代理ip地址列表（从http://www.xicidaili.com/获得）
	 * @param country 城市名称的拼音，默认sc（四川）
	 * @param time 	发布时间，默认今天
	 * @param speed 链接速度（待定）
	 * @return
	 * @throws IOException 
	 */
	public static List<IpBean> getFreeProviderList(String country,String time,Integer speed) throws IOException{
		List<IpBean> ipList = new ArrayList<IpBean>();
		if(null==time){
			time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		String url = "http://www.xicidaili.com/"+time+"/"+country;
		String code = getHTMLCode(url,new HashMap<String, Object>());
		Document document = Jsoup.parse(code);
		Elements elementsTr = document.select(".province table tr");
		for (int i = 1; i < elementsTr.size(); i++) { // 去掉第一个
			Element element = elementsTr.get(i);
			IpBean bean = new IpBean();
			bean.setIpAddress(element.select("td").eq(1).text()); // ip地址
			bean.setIpPort(element.select("td").eq(2).text()); // 端口
			ipList.add(bean);
		}
		return ipList;
	}
	
	/**
	 * 得到url的跟路径
	 * @param url 原始url
	 * @return 不包括最后的 /
	 */
	public static String getRootUrl(String url){
		Integer endIndex = url.indexOf("/", url.indexOf("/")+2); // 第三个/的位置
		String rootUrl = url.substring(0, endIndex);
		return rootUrl;
	}
	/**
	 * @param imageUrl 图片地址
	 * @return 图片名称.后缀（如 ico_set.gif）
	 * 现在把重名的图片，比如（http://a/b/a.jpg会和http://a/c/.../a.jpg覆盖），更名为网址的项目路径下的图片（项目路径...》图片名称.后缀）
	 */
	public static String getImageNameAndHouzui(String imageUrl){
		/* 原来的
		String result = imageUrl.substring(imageUrl.lastIndexOf("/")+1, imageUrl.length());
		if(result.contains("?")){
			result = result.substring(0, result.indexOf("?"));
		}*/
		
		// 现在的
		String result = imageUrl.substring(imageUrl.lastIndexOf("/")+1, imageUrl.length());
		if(result.contains("?")){
			result = result.substring(0, result.indexOf("?"));
		}
		String resultFull = imageUrl.substring(getRootUrl(imageUrl).length()+1, imageUrl.length()); // 包括了图片目录的
		if(resultFull.contains("?")){
			resultFull = resultFull.substring(0, resultFull.indexOf("?"));
		}
//		System.out.println(resultFull);
//		System.out.println(result);
//		System.out.println("-----");
		// 重置result的图片名称为目录名称
		String[] item = resultFull.split("/");
		for (int i = 0; i < item.length-1; i++) {
//			System.out.println(item[i]);
			result = item[i] + "》" + result;
		}
		return result;
	}
}


/**
 * 装载ip地址的类
 */
class IpBean{
	private String ipAddress = null; // IP地址
	private String ipPort	 = null; // 端口
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getIpPort() {
		return ipPort;
	}
	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}
	@Override
	public String toString() {
		return "IpBean [ipAddress=" + ipAddress + ", ipPort=" + ipPort + "]";
	}
}