package com.gp.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gp.json.JSONArray;
import com.gp.json.JSONObject;


/**
 * @author 高攀
 * @下午4:59:17
 */
public class test {

	public static void main(String[] args) {
		
//		System.out.println(validateImageUrl("http://xxxsadasd.jpa"));
		try {
			/*GetMethod getMethod = new GetMethod("http://www.qingniantuzhai.com/home");
			HttpClient client = new HttpClient();
			client.executeMethod(getMethod);
			String html = getMethod.getResponseBodyAsString();
			Document doc = Jsoup.parse(html);
			Elements ele = doc.select(".col-md-8 .row");
			for (Element element : ele) {
				System.out.println(element);
//				System.out.println(element.select(".col-md-7 a").text());
			}*/
//			String trace_date = "[{\"latitude\":30.579867,\"longitude\":104.067326,\"name\":\"点0\",\"description\":\"点0\"},{\"latitude\":30.57567,\"longitude\":104.068224,\"name\":\"点1\",\"description\":\"点1\"},{\"latitude\":30.576167,\"longitude\":104.073434,\"name\":\"点2\",\"description\":\"点2\"},{\"latitude\":30.579836,\"longitude\":104.073542,\"name\":\"点3\",\"description\":\"点3\"}]";
			String trace_date = "[{\"name\":\"%@\",\"describe\":\"%@\",\"longitude\":\"104.067062\",\"latitude\":\"30.582426\"}]";
			String picture_url = null;
			/** 构造url **/
			// 请求头
			StringBuilder temp = new StringBuilder("http://api.map.baidu.com/staticimage?");
			// center参数：center=104.07920100000001,30.575104500000002 & 
			StringBuilder centerBuffer = new StringBuilder("center=104.07920100000001,30.57510450000000220%&20%");
			// width参数，height参数：
			String width = "width="+700;
			String height = "&height="+500;
			// paths参数： &paths=104.076345,30.579598;104.059133,30.575338;104.071961,30.573753;104.075644,30.573955;104.07789,30.577297;104.080638,30.577771;104.079866,30.575019;104.079866,30.575019;104.081079,30.575426;104.081186,30.573965;104.081186,30.573965"
			StringBuilder pathsBuffer = new StringBuilder("&paths=");
			JSONArray jsona = new JSONArray(trace_date);
			for (int i = 0; i < jsona.length(); i++) {
				JSONObject json = jsona.getJSONObject(i);
				double anchor_longitude_temp = json.getDouble("longitude");
				double anchor_latitude_temp =  json.getDouble("latitude");
				pathsBuffer.append(anchor_longitude_temp+","+anchor_latitude_temp+";");
			}
			// 去掉最后一个;
			pathsBuffer = new StringBuilder(pathsBuffer.toString().substring(0, pathsBuffer.toString().length()-1));
			// pathStyles参数：&pathStyles=0xff000,5,1
			StringBuilder pathStylesBuffer = new StringBuilder("&pathStyles=0xff000,5,1");
			picture_url = temp.toString()+width+height+pathsBuffer.toString()+pathStylesBuffer.toString();
			System.out.println(picture_url);
			String curdesktop = System.getProperty("user.home")+"\\Desktop\\CatchImages\\";
			
//			String temmp = "http://api.map.baidu.com/staticimage?center=104.07920100000001,30.575104500000002%20&%20width=700&height=500&paths=104.082335,30.651678;104.086215,30.641115;104.075148,30.636205;104.066524,30.641363;104.067171,30.651244&pathStyles=0xff000,5,1";
			GetMethod getMethod = new GetMethod(picture_url);
			HttpClient client = new HttpClient();
			
			client.executeMethod(getMethod);
			InputStream inputStream = getMethod.getResponseBodyAsStream();
			File file = new File(curdesktop);
			if(!file.exists()){
				file.mkdirs();
			}
			byte b[] = {1};
			int size = 0;
			FileOutputStream outputStream = new FileOutputStream(new File(curdesktop+"asaa.png"));
			while((size=inputStream.read(b))!=-1){
				outputStream.write(b, 0, size);
			}
			outputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 验证图片地址是不是正确
	public static boolean validateImageUrl(String url){
		return Pattern.compile("^(http://)([^\\s]*\\.[^\\s]{3,4})").matcher(url).matches();
	}

	/** 
	 * 判断文件的编码格式 
	 * @param fileName :file 
	 * @return 文件编码格式 
	 * @throws Exception 
	 */  
	public static String codeString(String fileName) throws Exception{  
	    BufferedInputStream bin = new BufferedInputStream(  
	    new FileInputStream(fileName));  
	    int p = (bin.read() << 8) + bin.read();  
	    String code = null;  
	      
	    switch (p) {  
	        case 0xefbb:  
	            code = "UTF-8";  
	            break;  
	        case 0xfffe:  
	            code = "Unicode";  
	            break;  
	        case 0xfeff:  
	            code = "UTF-16BE";  
	            break;  
	        default:  
	            code = "GBK";  
	    }  
	      
	    return code;  
	}
	/**
	 * 获取指定长度的随机字符串
	 * @param length
	 * @return 结果
	 */
	public static String getRandomString(Integer length){
		String base = "";
		// A-Z : 65-90 | a-z : 97-122 
		// 得到A-Z的数
		char A = (char) getRangeNum(65, 90);
		// 得到a-z的数
		char a = (char) getRangeNum(97, 122);
		for (int i = 0; i < length; i++) {
			System.out.print((char) getRangeNum(65, 90));
			System.out.print((char) getRangeNum(97, 122));
		}
		return "";
	}
	
	/**
	 * 生成在[min,max]之间的随机整数
	 * @param min 最小值
	 * @param max 最大值
	 * @return 结果
	 */
	public static int getRangeNum(int min,int max){
		Random random = new Random();
    	int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}
	
}
class myE extends Exception{
	public myE() {
		
	}
	public myE(String msg) {
		super(msg);
	}
	
}
class myEA extends Exception{
	public myEA() {
		
	}
	public myEA(String msg) {
		super(msg);
	}
	
}