package com.gp.HTTP请求;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.gp.HTML解析.HTMLParserHelper;
import com.gp.json.JSONArray;
import com.gp.json.JSONObject;
import com.gp.实体类.FilmBean;

/**
 * @author 高攀
 * @下午 2015-9-23 3:49:10
 */

public class HTMLCatcher {

	public static void main(String[] args) {
		/*Map<String, Object> reqParam = new HashMap<String, Object>();
		try {
			getFilmSourceAndSearch2("小时代",1).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		/*try {
			String url = "http://localhost:8080/wot.kongzhong.com/film.security"
					+ "?index=" + 0 + "&count=" + 10;
			HttpMethod httpMethod = new GetMethod(url);
			HttpClient client = new HttpClient();
			client.executeMethod(httpMethod);
			String data = httpMethod.getResponseBodyAsString();
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.readValue(data, List.class));
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		getFilmSource1();
		/*try {
			HttpMethod httpMethod = new GetMethod("http://user.qzone.qq.com/1126755965/infocenter?ptsig=ADJ3Fpy1cJj1MVp5Qz4Pbf*MbVRmXoLADTEm9b0WMi4_");
			HttpClient client = new HttpClient();
			httpMethod.addRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
			httpMethod.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
			httpMethod.addRequestHeader("Avail-Dictionary", "XprLfaXG");
			httpMethod.addRequestHeader("Cache-Control", "max-age=0");
			httpMethod.addRequestHeader("Connection", "keep-alive");
			httpMethod.addRequestHeader("Cookie", "hasShowWeiyun1126755965=1; __Q_w_s__QZN_TodoMsgCnt=1; __Q_w_s_hat_seed=1; __Q_w_s__appDataSeed=1; pgv_pvid=5426857907; pgv_info=ssid=s2065473618; pt_clientip=4f7dabdd24e1ac88; pt_serverip=1c070a9319e3179b; pt2gguin=o1126755965; uin=o1126755965; skey=@iI93viCAw; ptisp=ctc; RK=3pF+AiedN0; qzone_check=1126755965_1443074486; ptcz=17bc4a07668399dab83926ceb87b031c88335cc00a053e484aa23674b10ec9ca; Loading=Yes; qzspeedup=sdch; p_skey=Qj4LwJCO6hlETz5lZfYyuOBMX3HTV4oUzJAGVGe6rHc_; pt4_token=-Uyn4aAaN5w9uPVsVMq4oA__; qz_screen=1920x1080; QZ_FE_WEBP_SUPPORT=1; cpu_performance_v8=3");
			httpMethod.addRequestHeader("Host", "user.qzone.qq.com");
			// 上次修改的时间
			httpMethod.addRequestHeader("If-Modified-Since", "Thu, 24 Sep 2015 02:55:30 GMT");
			httpMethod.addRequestHeader("Upgrade-Insecure-Requests", "1");
			httpMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36ozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36");
			System.out.println("---------------请求头------------------");
			Header[] headesrq = httpMethod.getRequestHeaders();
			for (Header header : headesrq) {
				System.out.println(header.getName()+" "+header.getValue());
			}
			
			client.executeMethod(httpMethod);
			System.out.println("---------------响应代码------------------");
			System.out.println(httpMethod.getStatusCode());
			System.out.println("---------------响应头------------------");
			Header[] headesrp = httpMethod.getResponseHeaders();
			for (Header header : headesrp) {
				System.out.println(header.getName()+" "+header.getValue());
			}
			System.out.println("---------------响应html------------------");
			InputStream inputStream = httpMethod.getResponseBodyAsStream();
			GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
			InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream,Charset.forName("utf-8"));
			BufferedReader bin21 = new BufferedReader(inputStreamReader);
			while(bin21.readLine()!=null){
				System.out.println(bin21.readLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	

	@Test
	// 解析 http://www.id97.com/ 或者 http://www.id97.com/videos/movie?page=x （最新）的资源 ，并且存至数据库
	// 参数：?page=x
	public static void getFilmSource1(){
		try {
			for (int i = 0; i <= 231; i++) {
				Date date1 = new Date(); // 当前时间
				List<FilmBean> filmList = new ArrayList<FilmBean>();
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("page", i);
				String htmlCode = HTMLParserHelper.getHTMLCode("http://www.id97.com/videos/movie",map);
				
				Document doc = Jsoup.parse(htmlCode);
				Elements elements = doc.select(".movie-item");
				for (Element element : elements) {
					FilmBean bean = new FilmBean();
					bean.setFilmName(element.select("a img").attr("alt"));
					bean.setFilmImage(element.select("a img").attr("src"));
					bean.setFilmScore(element.select(".meta div span").text());
					bean.setFilmType(element.select(".meta div[class$=otherinfo] a").text());
					// 再次请求每一个电影获得详细信息 http://www.id97.com/videos/resource/id/xxx.html
					String urlDetail = element.select("a").attr("href");
//					System.out.println("当前网址："+urlDetail);
					String codeDetail = "";
					try {
						codeDetail = HTMLParserHelper.getHTMLCode(urlDetail, new HashMap<String, Object>());
					} catch (IOException e) {
						System.out.println("网址有误！可能已经失效。。。无法获取["+bean.getFilmName()+"]["+urlDetail+"]的详细信息");
					}
					Document docDetail = Jsoup.parse(codeDetail);
					
					// 上方的介绍部分 （取得介绍，并且展开全部介绍，把介绍隐藏的部分显示出来，不需要，text的话不显示的也可以拿出来）
					Element elementsIntro = docDetail.select(".col-md-8").first();
					Element elementSummary = elementsIntro.select("p[class$=summary]").first();
//					elementSummary.select("span").attr("style","display: inline;");
					bean.setFilmIntro(elementSummary.text().replace("展开全部", "")); 
					
					// 下方的下载信息的div部分，其实就只有一个这个div节点
					Element elementsDetail = docDetail.select(".resource-list").first();
					if(elementsDetail.select("table").size()>0){
						Element elementTable = elementsDetail.select("table").first(); // 取得第一个table，如果有table节点的话才解析
						// 得到table下的所有tr
						Elements elementsTr = elementTable.select("tr");
						for (Element element2 : elementsTr) {
							// 得到每一个tr下面的第一个td下面span，判断是什么类型的链接
							String spanText = element2.select("td").first().select("span").first().text();
							if(null!=spanText){
								if("网盘".equals(spanText.trim())){
									// 请求百度链接，不失效才抓取
									String codeBaiduUrl = HTMLParserHelper.getHTMLCode(element2.select("td").eq(1).select("div a").attr("href"), new HashMap<String, Object>());
									if(codeBaiduUrl.contains("分享的文件已经被取") || codeBaiduUrl.contains("不合法") || codeBaiduUrl.contains("违规")){
										System.out.println("该百度链接已失效！"+element2.select("td").eq(1).select("div a").attr("href"));
										continue;
									}
									bean.setFilmBaiDuUrl(element2.select("td").eq(1).select("div a").attr("href")); // 第二个td的div下的a标签里面包含链接信息
									bean.setFilmBaiDuUrlPass(element2.select("td").eq(1).select("div strong").text().trim()); // 百度链接密码
								}else if("磁力".equals(spanText.trim()) || "电驴".equals(spanText.trim())){
									bean.setFilmMagnetUrl(element2.select("td").eq(1).select("div a").attr("href"));
								}
							}
						}
					}
					// 从上面的elementsDetail设置在线播放地址
					if(elementsDetail.select("ul").size()>0){ // 如果有的话
						Element elementsDetailUL = elementsDetail.select("ul[class$=list-group]").first();
						if(elementsDetailUL.select("li a").size()>0){
							String playLink = elementsDetailUL.select("li a").attr("href");
							if('/'==elementsDetailUL.select("li a").attr("href").trim().charAt(0)){ // 本网站的视频
								playLink = "http://www.id97.com"+playLink;
							}
							// 链接到播放地址，取出真实播放链接
							String playCode = "";
							try {
								playCode = HTMLParserHelper.getHTMLCode(playLink, new HashMap<String, Object>());
							} catch (IOException e) {
								System.out.println("网址有误！可能已经失效。。。无法获取["+bean.getFilmName()+"]["+playLink+"]的播放地址");
							}
							Document docPlay = Jsoup.parse(playCode);
							if(docPlay.select(".container-fluid div").size()>0){
								String realPlayLink = docPlay.select(".container-fluid div").first().select("iframe").attr("src");
								bean.setFilmPlayUrl(realPlayLink);
							}
						}
					}
					// 设置一下时间
					bean.setFilmTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					// 加入列表
					filmList.add(bean);
					saveFilmToMysql(bean);
				}
				Date date2 = new Date(); // 操作后的时间 
				long l = date2.getTime() - date1.getTime();
				long day = l / (24 * 60 * 60 * 1000);
				long hour = (l / (60 * 60 * 1000) - day * 24);
				long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
				long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
				System.out.println("第"+i+"次抓取完成！抓取了"+filmList.size()+"条数据。耗时：["+ day + "天" + hour + "小时" + min + "分" + s + "秒]");
				System.out.println("平均："+(Double.parseDouble(filmList.size()+"")/Double.parseDouble(s+""))+"条"+"/s");
				System.out.println("预计还需："+(400-i)*40/((Double.parseDouble(filmList.size()+"")/Double.parseDouble(s+"")))+"s"+"\t "+((400-i)*40/((Double.parseDouble(filmList.size()+"")/Double.parseDouble(s+""))))/60+"分钟");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 第1个搜索引擎
	 * 从www.id97.com网站查询电影资源(只返回了 名称 和 下载链接)
	 * @param filmName
	 * 搜索程式：http://www.id97.com/videos/search_ajax/name/xx
	 */
	public static List<FilmBean> getFilmSourceAndSearch1(String filmName) throws Exception{
		try {
			if(null==filmName){
				throw new Exception("搜索的名称不能为空！");
			}
			List<FilmBean> filmList = new ArrayList<FilmBean>();
			try {
				filmName = URLEncoder.encode(filmName, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			// 解析查询结果
			URL url = new URL("http://www.id97.com/videos/search_ajax/name/"+filmName);
			URLConnection con = url.openConnection();
			// 设置通用的请求属性
			con.setRequestProperty("accept", "*/*");
			con.setRequestProperty("connection", "Keep-Alive");
			con.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			con.setDoOutput(true);
			con.setDoInput(true);
			con.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String lines = null;
			StringBuffer result = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				result.append(lines);
			}
			String preFix = "magnet:?xt=urn:btih:";
			JSONArray json = new JSONArray(result.toString());
			for (int i = 0; i < json.length(); i++) {
				FilmBean bean = new FilmBean();
				JSONObject jsono = json.getJSONObject(i);
				bean.setFilmName(jsono.get("name")+"");
				bean.setFilmMagnetUrl(preFix+jsono.get("magnet"));
				filmList.add(bean);
			}
			return filmList;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 第2个搜索引擎
	 * 从http://www.sopanpan.com/网站查询电影资源(返回了 名称 和 下载链接)
	 * @param filmName
	 * @param curPage 当前页，从1开始
	 * 搜索程式：http://www.sopanpan.com/search/xx-0-0.html（第一个0[0-7]：依次代表全部、视频、音频、图片、文档、软件、压缩包、种子；第二个0[0-?]代表第几页）
	 * 
	 */
	public static List<FilmBean> getFilmSourceAndSearch2(String filmName,Integer curPage) throws Exception{
		try {
			List<FilmBean> filmList = new ArrayList<FilmBean>();
			if(null==filmName){
				throw new Exception("搜索的名称不能为空！");
			}
			String urlInit = "http://www.sopanpan.com/search/"+filmName+"-0-"+(curPage-1)+".html";
			String code = HTMLParserHelper.getHTMLCode(urlInit, new HashMap<String, Object>());
			Document document = Jsoup.parse(code);
			/** 获得总页数 **/
			String page = document.select("div[class$=sub_pgsec_c]").select("table tr td").eq(0).text().split("/")[1].replaceAll("页", "");
			Integer sumPage = Integer.parseInt(page); // 得到查询结果的总页数
			if(curPage>sumPage){
				throw new Exception("大于了总页数");
			}
			/** 循环获得详细数据 **/
			Elements elementsLi = document.select(".radius_b div[class$=ulst1] ul li:not(.t)"); // 不要第一行class为t的（介绍部分）
			Integer i = 0;
			for (Element element : elementsLi) {
				FilmBean bean = new FilmBean();
				bean.setFilmName(element.select("span[class$=_l] a").text());
				String urlDetail = "http://www.sopanpan.com"+element.select("span[class$=_l] a").attr("href");
				String codeDetail = HTMLParserHelper.getHTMLCode(urlDetail, new HashMap<String, Object>());
				Document documentDetail = Jsoup.parse(codeDetail);
				if(documentDetail.select("div[class$=flist] .lst ul li a").size()>0){
					Element elementDetail = documentDetail.select("div[class$=flist] .lst ul li a").first();
					String baiduUrl = elementDetail.attr("targeturl");
					// 请求百度链接，不失效才抓取
					String codeBaiduUrl = HTMLParserHelper.getHTMLCode(baiduUrl, new HashMap<String, Object>());
					if(codeBaiduUrl.contains("分享的文件已经被取") || codeBaiduUrl.contains("不合法") || codeBaiduUrl.contains("违规")){
						continue;
					}
					bean.setFilmBaiDuUrl(elementDetail.attr("targeturl"));
				}
				i++;
				if(null == bean.getFilmBaiDuUrl() || "null".equals(bean.getFilmBaiDuUrl()) || null == bean.getFilmBaiDuUrl()){
					continue;
				}
				System.out.println(bean.getFilmName()+"\t<--->\t"+bean.getFilmBaiDuUrl());
				filmList.add(bean);
			}
			return filmList;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	

	// 保存到数据库
	public static void saveFilmToMysql(FilmBean bean) {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/myfilmdb", "root", "123456");
			con.setAutoCommit(false);
//			String sql = "INSERT INTO `filmbean` (`filmName`, `filmType`, `filmTag`, `filmIntro`, `filmTime`, `filmScore`, `filmMagnetUrl`, `filmPlayUrl`, `filmBaiDuUrl`, `filmBaiDuUrlPass`, `filmTorrent`)"
//					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String sql = "INSERT INTO `filmbean_url` (`filmName`, `filmType`, `filmTag`, `filmIntro`, `filmTime`, `filmScore`, `filmMagnetUrl`, `filmPlayUrl`, `filmBaiDuUrl`, `filmBaiDuUrlPass`, `filmTorrent`,`filmImage`)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, bean.getFilmName());
			preparedStatement.setString(2, bean.getFilmType());
			preparedStatement.setString(3, bean.getFilmTag());
			preparedStatement.setString(4, bean.getFilmIntro());
			preparedStatement.setString(5, bean.getFilmTime());
			preparedStatement.setString(6, bean.getFilmScore());
			preparedStatement.setString(7, bean.getFilmMagnetUrl());
			preparedStatement.setString(8, bean.getFilmPlayUrl());
			preparedStatement.setString(9, bean.getFilmBaiDuUrl());
			preparedStatement.setString(10, bean.getFilmBaiDuUrlPass());
			preparedStatement.setString(11, bean.getFilmTorrent());
			preparedStatement.setString(12, bean.getFilmImage());
			preparedStatement.executeUpdate();
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
