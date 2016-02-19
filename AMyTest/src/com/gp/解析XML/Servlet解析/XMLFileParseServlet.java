package com.gp.解析XML.Servlet解析;
// servlet类
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//import com.keyhua.bp.filecontrol.FileUploadManager;
//import com.keyhua.protocol.json.JSONArray;
//import com.keyhua.protocol.json.JSONException;
//import com.keyhua.protocol.json.JSONObject;

public class XMLFileParseServlet {/*

	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("=================================");
		this.doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("=================================");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		JSONObject result = new JSONObject();
		try{
			// 判断上传的类型是否是 enctype="multipart/form-data"  true是  false表示不是 
			boolean flag=ServletFileUpload.isMultipartContent(request);
			DiskFileItemFactory facoty = new DiskFileItemFactory();
			facoty.setSizeThreshold(1*1024*1024);
			facoty.setRepository(new File(System.getProperty("java.io.tmpdir")));
			request.setCharacterEncoding("UTF-8");
			ServletFileUpload upload = new ServletFileUpload(facoty);
			//设置上传文件的大小,如果上传文件大于设置的大小，则要手动抛出SizeLimitExceededException 异常 
			upload.setFileSizeMax(FileUploadManager.getInstance().getMaxUploadFileSize());
			upload.setSizeMax(FileUploadManager.getInstance().getMaxUploadRequestSize());

			//解析request请求,返回集合[集合中存放FileItem] 
			@SuppressWarnings("unchecked")
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> it = items.iterator();
			InputStream inputStream = null;
//			while(it.hasNext()){
				FileItem item = it.next();
				inputStream = item.getInputStream();
//			}
			String name1 = item.getName();
			if(!Pattern.compile("[^\\s]*\\.kml$").matcher(name1).matches()){
				out.write("文件格式错误！");
				return ;
			}
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
//			InputStream inputStream = request.getInputStream();
			builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			
			*//** 1、Document元素 **//*
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
			
			*//** 2、Folder元素 **//*
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
//					System.out.println("\t"+nodeName);
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
							
							*//** 3、每个Placemark元素 **//*
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
			
			*//** 生成picture_url **//*
			String trace_date = array_trace_data.toString();
			String picture_url = null;
			picture_url = getBaiDuAPIPicUrl(trace_date);
			jsonOut.put("picture_url", picture_url);
			System.out.println(jsonOut.toString());
			*//** 构造url end **//*
			
			out.write(jsonOut.toString());
		}catch(Exception e){
            try {
            	result.put("msg", e.getMessage());
            	result.put("state", 0);
	            return;
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			out.write("数据出错了");
            e.printStackTrace();
		}
		finally{
            out.close();
		}
	}
	public static String getBaiDuAPIPicUrl(String trace_date){
		*//** 生成picture_url **//*
		String picture_url = null;
		*//** 构造url **//*
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
	public static void main(String[] args) {
		String s = "asdasasdd.kml";
		System.out.println(Pattern.compile("[^\\s]*\\.kml$").matcher(s).matches());
	}
*/}
