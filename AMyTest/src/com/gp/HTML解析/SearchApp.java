package com.gp.HTML解析;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author 高攀
 * @下午 7:23:25
 */
public class SearchApp extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static JPanel jPanel = new JPanel();
	private static JTextField jTextField = new JTextField(20);
	private static JButton button = new JButton("search");
	
	private static JTextField jTextField1 = new JTextField("199",5);
	private static JButton button1 = new JButton("扫描页数");
	
	private static JTextArea area = new JTextArea(30, 80);
	private static JScrollPane scroll = new JScrollPane(area);
	public static String currentuserdesktop = System.getProperty("user.home")+"\\Desktop"; 
	public SearchApp() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("搜索");
		this.setSize(1000, 600);
		this.setVisible(true);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		// 样式
		area.setAutoscrolls(true);
		area.setLineWrap(true);
		// 注册组件
		jPanel.add(jTextField);
		jPanel.add(button);
		jPanel.add(jTextField1);
		jPanel.add(button1);
		jPanel.add(scroll);
		this.add(jPanel);
		// 事件
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Map<String, String> resultMap = parseClAndSearch(jTextField.getText().trim(),Integer.parseInt(jTextField1.getText().trim()));
					// 文件流
					Set<Entry<String, String>> set = resultMap.entrySet();
					for (Entry<String, String> entry : set) {
						writefiletotxt((new FileWriter(currentuserdesktop+"\\查找结果.txt",true)),("标题："+entry.getKey()+"\t链接："+entry.getValue()+"\r\n"));
					}
					area.append("\n----------------------------------------------------------------------------------------------------完毕----------------------------------------------------------------------------------------------------");
				} catch (Exception e2) {
					// 
					JOptionPane.showMessageDialog(null, "只能输入整数！", "错误", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
	}
	
	public static void main(String[] args) {
		new SearchApp();
	}

	/**
	 * 搜索关键词，用逗号分隔多条件
	 */
	public static Map<String, String> parseClAndSearch(String keyWord,Integer page){
		Map<String, String> resultMap = new TreeMap<String, String>(); // 结果-链接
		try {
			for (int i = 0; i < page; i++) { // 设置扫描的页数范围
					StringBuffer htmlCode = new StringBuffer("");
					HttpMethod httpMethod = new GetMethod("http://cl.clvv.biz/thread0806.php?fid=7&search=&page="+(i+1));
					HttpClient client = new HttpClient();
					httpMethod.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
					httpMethod.addRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
					httpMethod.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
					httpMethod.addRequestHeader("Referer", "http://cl.clvv.biz/thread0806.php?fid=7");
					httpMethod.addRequestHeader("HTTPS", "1");
					httpMethod.addRequestHeader("Connection", "keep-alive");
					httpMethod.addRequestHeader("Host", "cl.clvv.biz");
					httpMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36ozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36");
					
					client.executeMethod(httpMethod);
					InputStream inputStream = httpMethod.getResponseBodyAsStream();
					GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
					InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream,Charset.forName("gb2312"));
					BufferedReader bin21 = new BufferedReader(inputStreamReader);
					while(bin21.readLine()!=null){
						String line = bin21.readLine();
						htmlCode.append(line);
					}
					Document doc = Jsoup.parse(htmlCode.toString());
					Elements elementsTr = doc.select("table tr");
					for (Element element : elementsTr) {
						String title = element.select("td").eq(1).select("h3 a").text();
						if(null!=title && !"".equals(title)){
							String link = "http://cl.clvv.biz/"+element.select("td").eq(1).select("h3 a").attr("href");
							if(keyWord.contains(",")){ // 多条件
								String[] key = keyWord.split(",");
								for (String string : key) {
									if(title.contains(string)){
//										System.out.println("\n"+title+"："+link);
										resultMap.put(title, link);
										area.append("\n"+title+"："+link);
									}
								}
							}else {
								if(title.contains(keyWord)){
//									System.out.println("\n"+title+"："+link);
									resultMap.put(title, link);
									area.append("\n"+title+"："+link);
								}
							}
							jPanel.paintImmediately(jPanel.getBounds());
						}
					}
					// 释放链接
					httpMethod.abort();
					httpMethod.releaseConnection();
					
					resultMap.remove("");
//					System.out.print("第"+(i+1)+"页... ");
					
			}
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
}
