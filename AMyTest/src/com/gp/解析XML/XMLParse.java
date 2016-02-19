package com.gp.解析XML;

// 需导包:xml-apis.jar ;文件案例在 ./simple
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gp.json.JSONArray;
import com.gp.json.JSONObject;

public class XMLParse {
	Document document = null;

	NodeList allNode = null;

	// 构造函数，初始化Document对象
	public XMLParse() {
		// 指定File文件
		File file = new File("MyXml.xml");

		// 建立DocumentBuilderFactory对象
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder builder;
		try {
			// 建立DocumentBuilder对象
			builder = builderFactory.newDocumentBuilder();
			// 用DocumentBuilder对象的parse方法引入文件建立Document对象
			document = builder.parse(file);
			allNode = document.getChildNodes();

			// 测试1：找出所有person标签，返回NodeList
			NodeList person = document.getElementsByTagName("person");
			// 按条件输出peron标签中的属性和值
			showByCondition(person);

			// 测试2：遍历所有节点
			searchAndShow(allNode);

			// 测试3：按标签名查找输出
			this.SearchByCondition(person);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("找不到你指定的文件！");
			e.printStackTrace();
		}
	}

	// 例：寻找遍历
	public void searchAndShow(NodeList allNode) {
		System.out.println();
		System.out.println("------searchAndShow输出结果-------");
		for (int i = 0; i < allNode.getLength(); i++) {
			// 得到一个节点，需要强制转换为Element，这里Node是Element的父类
			Node node = allNode.item(i);
			if (!node.getNodeName().equals("#text")) {
				System.out.println("节点名称：" + node.getNodeName());
			}
			// System.out.println(element.getAttribute(""));

			if (node.getChildNodes().getLength() > 3) {
				// 若包含子节点，则递归遍历
				System.out.println("此节点包含子元素！");
				searchAndShow(node.getChildNodes());
			} else {
				// 若不包含子节点，则输出节点中的内容
				if (!node.getTextContent().trim().equals("")
						&& node.getTextContent() != null) {
					System.out.println("节点值：" + node.getTextContent());
				}
			}
		}
	}

	// 按条件输出
	public void showByCondition(NodeList allNode) {
		System.out.println();
		System.out.println("------showByCondition输出结果-------");
		Element element;
		// 对符合条件的所有节点进行遍历
		for (int i = 0; i < allNode.getLength(); i++) {
			// 获得一个元素
			element = (Element) allNode.item(i);
			// 输出这个元素的personid属性
			System.out.println(element.getAttribute("personid"));
			// 此元素有子节点，获取所有子节点，返回一个personList
			NodeList personList = element.getChildNodes();
			// 遍历所有子节点
			for (int j = 0; j < personList.getLength(); j++) {
				// 若子节点的名称不为#text，则输出，#text为反／标签
				if (!personList.item(j).getNodeName().equals("#text")) {
					// 输出节点名称、节点值
					System.out.println(personList.item(j).getNodeName() + ":"
							+ personList.item(j).getTextContent());
				}
			}
		}
	}

	// 按标签名寻找节点
	public void SearchByCondition(NodeList allNode) {
		System.out.println();
		System.out.println("------SearchByCondition输出结果-------");
		Element element;
		// 对符合条件的所有节点进行遍历
		for (int i = 0; i < allNode.getLength(); i++) {
			// 用document来查找所有name标签，得到一个标签后输出值
			String name = document.getElementsByTagName("name").item(i)
					.getTextContent();
			System.out.println("姓名：" + name);

			// 用document来查找所有tel标签，得到一个标签后输出值
			String tel = document.getElementsByTagName("tel").item(i)
					.getTextContent();
			System.out.println("电话：" + tel);

			// //用document来查找所有tel标签，得到一个标签后输出值
			String sex = document.getElementsByTagName("email").item(i)
					.getTextContent();
			System.out.println("email：" + sex);
			System.out.println("===================");
		}
	}

	public static void main(String[] args) {
		
		JSONArray arrayOut = new JSONArray();
		// 指定File文件
		File file = new File("C:\\Users\\liupei\\Desktop\\岗仁波齐转山.kml");

		// 建立DocumentBuilderFactory对象
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			InputStream inputStream = new FileInputStream(file);
			// 建立DocumentBuilder对象
			builder = builderFactory.newDocumentBuilder();
			// 用DocumentBuilder对象的parse方法引入文件建立Document对象
			Document document = builder.parse(inputStream);
			/** 1、Document元素 **/
			NodeList nodeList_Document = document.getElementsByTagName("Document");
			if(nodeList_Document.getLength()<=0){
				System.out.println("缺少Document元素！");
				return ;
			}
			Node node_Document = nodeList_Document.item(0);
			if(node_Document==null){
				System.out.println("缺少Document元素！");
				return ;
			}
			
			JSONObject jsonOut = new JSONObject();
			/** 2、Folder元素 **/
			NodeList nodeList_Folder = document.getElementsByTagName("Folder");
			if(nodeList_Folder.getLength()<=0){
				System.out.println("缺少Document元素！");
				return ;
			}
			Node node_Folder = nodeList_Folder.item(0);
			if(node_Folder==null){
				System.out.println("缺少Folder元素！");
				return ;
			}
			NodeList node_Folder_childs = node_Folder.getChildNodes();// 得到Folder下面的子元素
			JSONArray array_trace_data = new JSONArray();
			StringBuilder builder2 = new StringBuilder("");
			for (int i = 0; i < node_Folder_childs.getLength(); i++) {
				Node itemOut = node_Folder_childs.item(i);
				String nodeName = itemOut.getNodeName();
//				System.out.println("\t"+nodeName);
				if("name".equals(nodeName)){
					// 得到外层的name
					String name = itemOut.getTextContent();
					jsonOut.put("name", name);
				}
				if("Placemark".equals(nodeName)){
					JSONObject json_item = new JSONObject();
					if(itemOut.hasChildNodes()){
						NodeList Placemark_childs = itemOut.getChildNodes();
						for (int j = 0; j < Placemark_childs.getLength(); j++) {
							/** 3、每个Placemark元素 **/
							Node Placemark_Node = Placemark_childs.item(j);
							if(!Placemark_Node.hasChildNodes()){
								continue;
							}
							String Placemark_node_name = Placemark_Node.getNodeName();
							String Placemark_node_value = Placemark_Node.getTextContent();
							if("Point".equals(Placemark_node_name)){
								String[] points= Placemark_node_value.split(",");
								String latitude = points[0];
								String longitude = points[1];
								json_item.put("latitude", latitude.trim());
								json_item.put("longitude", longitude.trim());
							}
							if("name".equals(Placemark_node_name)){
								json_item.put("name", Placemark_node_value);
							}
						}
						// 没有点就不添加
						if(json_item.has("latitude")){
							builder2.append(json_item.toString()+",");
						}
					}
				}
			}
			builder2 = new StringBuilder(builder2.toString().substring(0, builder2.toString().length()-1));
			array_trace_data = new JSONArray("["+builder2.toString()+"]");
//			System.out.println(array_trace_data);
			jsonOut.put("trace_data", array_trace_data.toString());
			
			/** 生成picture_url **/
			String trace_date = array_trace_data.toString();
			String picture_url = null;
			picture_url = getBaiDuAPIPicUrl(trace_date);
			jsonOut.put("picture_url", picture_url);
			System.out.println(jsonOut.toString());
			/** 构造url end **/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getBaiDuAPIPicUrl(String trace_date){
		/** 生成picture_url **/
		String picture_url = null;
		/** 构造url **/
		// 请求头
		StringBuilder temp = new StringBuilder("http://api.map.baidu.com/staticimage?");
		String width = "width="+700;
		String height = "&height="+500;
		StringBuilder pathsBuffer = new StringBuilder("&paths=");
		try {
			JSONArray jsona = new JSONArray(trace_date);
			for (int i = 0; i < jsona.length(); i++) {
				JSONObject json = jsona.getJSONObject(i);
				double anchor_longitude_temp = json.getDouble("longitude");
				double anchor_latitude_temp =  json.getDouble("latitude");
				pathsBuffer.append(anchor_longitude_temp+","+anchor_latitude_temp+";");
			}
			// 去掉最后一个;
			pathsBuffer = new StringBuilder(pathsBuffer.toString().substring(0, pathsBuffer.toString().length()-1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// pathStyles参数：&pathStyles=0xff000,5,1
		StringBuilder pathStylesBuffer = new StringBuilder("&pathStyles=0xff000,5,1");
		picture_url = temp.toString()+width+height+pathsBuffer.toString()+pathStylesBuffer.toString();
		System.out.println(picture_url);
		
		return picture_url;
	}
}