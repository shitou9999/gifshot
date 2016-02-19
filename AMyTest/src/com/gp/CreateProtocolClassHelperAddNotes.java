package com.gp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gp.json.JSONArray;
import com.gp.json.JSONObject;

public class CreateProtocolClassHelperAddNotes {
	
	
	final static String currentuserdesktop = System.getProperty("user.home")+"\\Desktop\\ClassCreate\\";
//	final static String ServerName = "http://localhost:8080/OutdoorServer/Main";
	final static String ServerName = "http://localhost:8080/BJExpert/Main";
	final static String ServerName_FORMAL = "http://localhost:8080/BJExpert/Main";
	
	// 得到各种参数以及对应的注释 Map<字段名称,注释>
	static Map<String,String> mapRequest = new HashMap<String,String>();
	static Map<String,String> mapResponse = new HashMap<String,String>();
	static StringBuffer resourceRequest = new StringBuffer(""); // 去掉注释的文本串
	static StringBuffer resourceResponse = new StringBuffer(""); // 去掉注释的文本串
	
	public static void main(String[] args) {
		doInit(); // 初始化注释
		File file = new File(currentuserdesktop);
		if(!file.exists()){
			try {
				file.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Action名称
		String actionName= "GetCityFriendsListAction";
		// 请求参数（json格式）
		String requestFieldString = "{"+resourceRequest.toString()+"}";
		// 返回参数（json格式）
		String responseFieldString = "{"+resourceResponse.toString()+"}";
		System.out.println(requestFieldString+"===");
		System.out.println(responseFieldString);
		print4Class(actionName,requestFieldString,responseFieldString);
	}
	
	/**
	 * @param actionName Action名称
	 */
	public static void print4Class(String fullActionName,String requestFieldString,String responseFieldString){
		String baseActionName = fullActionName.replaceAll("Action", "");
		System.out.println("-----------请求类1开始------------");
		
		String fileName = baseActionName+"Request"+".java";
		StringBuffer result = new StringBuffer("");
		System.out.println(fileName);
		
		/** 信息 **/
		System.out.println("/**\r\n");
		result.append("/**\r\n");
		
		System.out.println(" * @author 高攀\r\n");
		result.append(" * @author 高攀\r\n");
		
		System.out.println(" * @date "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\r\n");
		result.append(" * @date "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\r\n");
		
		System.out.println(" */\r\n");
		result.append(" */\r\n");
		
		/** 信息end **/
		
		System.out.println("public class "+baseActionName+"Request"+" extends KeyhuaBaseRequest {");
		result.append("public class "+baseActionName+"Request"+" extends KeyhuaBaseRequest {\r\n");
		
		System.out.println("\tpublic "+baseActionName+"Request"+"(){");
		result.append("\tpublic "+baseActionName+"Request"+"(){\r\n");
		
		System.out.println("\t\tsetActionCode(ActionInfo."+fullActionName+".code());");
		result.append("\t\tsetActionCode(ActionInfo."+fullActionName+".code());\r\n");
		
		System.out.println("\t\tsetActionName(ActionInfo."+fullActionName+".name());");
		result.append("\t\tsetActionName(ActionInfo."+fullActionName+".name());\r\n");
		
		System.out.println("\t\tparameter = new "+baseActionName+"Request"+"Parameter();");
		result.append("\t\tparameter = new "+baseActionName+"Request"+"Parameter();\r\n");
		
		System.out.println("\t}");
		result.append("\t}\r\n");
		
		System.out.println("}");
		result.append("}\r\n");
		
		writefiletotxt(fileName, result.toString(), true);
		
		System.out.println("-----------结束------------");
		System.out.println("-----------请求类2开始------------");
		fileName = baseActionName+"RequestParameter"+".java";
		result = new StringBuffer("");
		System.out.println(fileName);
		doCreateTwoJson(requestFieldString,baseActionName,fileName);
		System.out.println("-----------结束------------");
		
		System.out.println("-----------响应类1开始------------");
		fileName = baseActionName+"Response"+".java";
		result = new StringBuffer("");
		System.out.println(fileName);
		
		/** 信息 **/
		System.out.println("/**\r\n");
		result.append("/**\r\n");
		
		System.out.println(" * @author 高攀\r\n");
		result.append(" * @author 高攀\r\n");
		
		System.out.println(" * @date "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\r\n");
		result.append(" * @date "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\r\n");
		
		System.out.println(" */\r\n");
		result.append(" */\r\n");
		
		/** 信息end **/
		
		
		System.out.println("public class "+baseActionName+"Response"+" extends KeyhuaBaseResponse{");
		result.append("public class "+baseActionName+"Response"+" extends KeyhuaBaseResponse{\r\n");
		
		System.out.println("\tpublic "+baseActionName+"Response"+"(){");
		result.append("\tpublic "+baseActionName+"Response"+"(){\r\n");
		
		System.out.println("\t\tsetActionCode(ActionInfo."+fullActionName+".code());");
		result.append("\t\tsetActionCode(ActionInfo."+fullActionName+".code());\r\n");
		
		System.out.println("\t\tsetActionName(ActionInfo."+fullActionName+".name());");
		result.append("\t\tsetActionName(ActionInfo."+fullActionName+".name());\r\n");
		
		System.out.println("\t\tpayload = new "+baseActionName+"Response"+"Payload();");
		result.append("\t\tpayload = new "+baseActionName+"Response"+"Payload();\r\n");
		
		System.out.println("\t}");
		result.append("\t}\r\n");
		
		System.out.println("}");
		result.append("}\r\n");
		
		writefiletotxt(fileName, result.toString(), true);
		
		System.out.println("-----------结束------------");
		
		System.out.println("-----------响应类2开始------------");
		fileName = baseActionName+"ResponsePayload"+".java";
		result = new StringBuffer("");
		System.out.println(baseActionName+"ResponsePayload");
		doCreateTwoJson(responseFieldString,baseActionName,fileName);
		System.out.println("-----------结束------------");
		

		// 打印测试类
		try {
			fileName = "Test"+fullActionName+".java";
			result = new StringBuffer("");
			System.out.println("---------------------TestClass--------------------");
			
			System.out.println("import junit.framework.TestCase;\n");
			result.append("import junit.framework.TestCase;\n\r\n");
			
			/** 信息 **/
			System.out.println("/**\r\n");
			result.append("/**\r\n");
			
			System.out.println(" * @author 高攀\r\n");
			result.append(" * @author 高攀\r\n");
			
			System.out.println(" * @date "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\r\n");
			result.append(" * @date "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\r\n");
			
			System.out.println(" */\r\n");
			result.append(" */\r\n");
			
			/** 信息end **/
			
			System.out.println("public class Test"+fullActionName+" extends TestCase {");
			result.append("public class Test"+fullActionName+" extends TestCase {\r\n");
			
			System.out.println("\tprotected void setUp() throws Exception {");
			result.append("\tprotected void setUp() throws Exception {\r\n");
			
			System.out.println("\t\tsuper.setUp();");
			result.append("\t\tsuper.setUp();\r\n");
			
			System.out.println("\t}");
			result.append("\t}\r\n");
			
			System.out.println("\tprotected void tearDown() throws Exception {");
			result.append("\tprotected void tearDown() throws Exception {\r\n");
			
			System.out.println("\t\tsuper.tearDown();");
			result.append("\t\tsuper.tearDown();\r\n");
			
			System.out.println("\t}");
			result.append("\t}\r\n");
			
			System.out.println("\tpublic void testHandleAction() {");
			result.append("\tpublic void testHandleAction() {\r\n");
			
			System.out.println("\t\t"+baseActionName+"Request"+" request = new "+baseActionName+"Request"+"();");
			result.append("\t\t"+baseActionName+"Request"+" request = new "+baseActionName+"Request"+"();\r\n");
			
			System.out.println("\t\t"+baseActionName+"RequestParameter"+" parameter = new "+baseActionName+"RequestParameter"+"();");
			result.append("\t\t"+baseActionName+"RequestParameter"+" parameter = new "+baseActionName+"RequestParameter"+"();\r\n");
			
			System.out.println("\t\trequest.setAuthenticationToken(\"\");");
			result.append("\t\trequest.setAuthenticationToken(\"\");\r\n");
			
			// 设置测试类请求参数部分
			JSONObject json = new JSONObject(requestFieldString);
			Iterator iterator1 = json.keys();
			Integer i = 0;
			while(iterator1.hasNext()){
				i++;
				String name = iterator1.next().toString();
				Object value = json.get(name);
				// 得到值的类型：String Double Integer JSONArray JSONObject
				String type = value.getClass().getSimpleName();
				if("Integer".equals(type)){
					System.out.println("\t\tparameter.set"+firstToUp(name)+"("+value+");");
					result.append("\t\tparameter.set"+firstToUp(name)+"("+value+");\r\n");
				}else if("String".equals(type)){
					System.out.println("\t\tparameter.set"+firstToUp(name)+"(\""+"测试数据"+i+"\");");
					result.append("\t\tparameter.set"+firstToUp(name)+"(\""+value+"\");\r\n");
				}else if("Double".equals(type)){
					System.out.println("\t\tparameter.set"+firstToUp(name)+"("+value+");");
					result.append("\t\tparameter.set"+firstToUp(name)+"("+value+");\r\n");
				}
			}
			i = null;
			System.out.println("\t\trequest.setParameter(parameter);\n");
			result.append("\t\trequest.setParameter(parameter);\n\r\n");
			
			System.out.println("\t\tString requestString = null;");
			result.append("\t\tString requestString = null;\r\n");
			
			System.out.println("\t\ttry {requestString = request.toJSONString();} catch (ProtocolMissingFieldException e) {e.printStackTrace();}");
			result.append("\t\ttry {requestString = request.toJSONString();} catch (ProtocolMissingFieldException e) {e.printStackTrace();}\r\n");
			
			System.out.println("\t\tString requestUrl = \"http://localhost:8080/"+ServerName+"/Main\";");
			result.append("\t\tString requestUrl = \"http://localhost:8080/"+ServerName+"/Main\";\r\n");
			
			// http://localhost:8080/OutdoorServer/Main
			System.out.println("\t\t// http://localhost:8080/"+ServerName+"/Main\r\n");
			result.append("\t\t// http://http://123.56.150.140/Main\r\n");
			// http://123.56.150.140/Main
			System.out.println("\t\t// http://123.56.150.140:8080/"+ServerName+"/Main\r\n");
			result.append("\t\t// http://123.56.150.140/Main\r\n");
			
			System.out.println("\t\tJSONRequestSender sender = new JSONRequestSender(requestUrl);\r\n\t\tJSONObject responseObject = null;\r\n");
			result.append("\t\tJSONRequestSender sender = new JSONRequestSender(requestUrl);\r\n\t\tJSONObject responseObject = null;\r\n");
			
			System.out.println("\t\ttry {responseObject = sender.send(new JSONObject(requestString));} catch (JSONException e) {e.printStackTrace();}");
			result.append("\t\ttry {responseObject = sender.send(new JSONObject(requestString));} catch (JSONException e) {e.printStackTrace();}\r\n");
			
			System.out.println("\t}");
			result.append("\t}\r\n");
			
			System.out.println("}");
			result.append("}\r\n");
			
			writefiletotxt(fileName, result.toString(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 生成Action类
		try {
			fileName = fullActionName+".java";
			result = new StringBuffer("");
			System.out.println("---------------------ActionClass--------------------");
			
			/** 信息 **/
			System.out.println("/**\r\n");
			result.append("/**\r\n");
			
			System.out.println(" * @author 高攀\r\n");
			result.append(" * @author 高攀\r\n");
			
			System.out.println(" * @date "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\r\n");
			result.append(" * @date "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\r\n");
			
			System.out.println(" */\r\n");
			result.append(" */\r\n");
			
			/** 信息end **/
			
			System.out.println("public class "+fullActionName+" extends KeyhuaBaseAction {");
			result.append("public class "+fullActionName+" extends KeyhuaBaseAction {\r\n");
			
			System.out.println("\tpublic "+fullActionName+"() {");
			result.append("\tpublic "+fullActionName+"() {\r\n");
			
			System.out.println("\t\tsuper.actionCode = ActionInfo."+fullActionName+".code();");
			result.append("\t\tsuper.actionCode = ActionInfo."+fullActionName+".code();\r\n");
			
			System.out.println("\t\tsuper.actionName = ActionInfo."+fullActionName+".name();");
			result.append("\t\tsuper.actionName = ActionInfo."+fullActionName+".name();\r\n");
			
			System.out.println("\t\tsuper.actionDescription = ActionInfo."+fullActionName+".description();");
			result.append("\t\tsuper.actionDescription = ActionInfo."+fullActionName+".description();\r\n");
			
			System.out.println("\t\tsuper.needAuthenticationCheck = false;");
			result.append("\t\tsuper.needAuthenticationCheck = false;\r\n");
			
			System.out.println("\t}");
			result.append("\t}\r\n");
			
			System.out.println("\t@Override");
			result.append("\t@Override\r\n");
			
			System.out.println("\tpublic ResponseMessageGenerator handleAction(RequestMessageParser parser) throws ProtocolInvalidMessageException, ProtocolMissingFieldException, JSONException {");
			result.append("\tpublic ResponseMessageGenerator handleAction(RequestMessageParser parser) throws ProtocolInvalidMessageException, ProtocolMissingFieldException, JSONException {\r\n");
			
			System.out.println("\t\tResponseMessageGenerator generator = new ResponseMessageGenerator();");
			result.append("\t\tResponseMessageGenerator generator = new ResponseMessageGenerator();\r\n");
			
			System.out.println("\t\t"+baseActionName+"Request request = new "+baseActionName+"Request();");
			result.append("\t\t"+baseActionName+"Request request = new "+baseActionName+"Request();\r\n");
			
			System.out.println("\t\trequest.fromJSONString(parser.getRequestMessage());");
			result.append("\t\trequest.fromJSONString(parser.getRequestMessage());\r\n");
			
			System.out.println("\t\t"+baseActionName+"RequestParameter parameter = ("+baseActionName+"RequestParameter)request.getParameter();");
			result.append("\t\t"+baseActionName+"RequestParameter parameter = ("+baseActionName+"RequestParameter)request.getParameter();\r\n");
			
			System.out.println("\t\t"+baseActionName+"ResponsePayload payload = new "+baseActionName+"ResponsePayload();");
			result.append("\t\t"+baseActionName+"ResponsePayload payload = new "+baseActionName+"ResponsePayload();\r\n");
			
			System.out.println("\t\tString aut = request.getAuthenticationToken();");
			result.append("\t\tString aut = request.getAuthenticationToken();\r\n");
			
			System.out.println("\t\tHWTXAuthSessionManager HWTXSession = HWTXAuthSessionManager.getInstance();");
			result.append("\t\tHWTXAuthSessionManager HWTXSession = HWTXAuthSessionManager.getInstance();\r\n");
			
			System.out.println("\t\tString userid = null;");
			result.append("\t\tString userid = null;\r\n");
			
			System.out.println("\t\tif(null != aut && !\"\".equals(aut)){");
			result.append("\t\tif(null != aut && !\"\".equals(aut)){\r\n");
			
			System.out.println("\t\t\tuserid = HWTXSession.getAuthTokenOwner(aut);");
			result.append("\t\t\tuserid = HWTXSession.getAuthTokenOwner(aut);\r\n");
			
			System.out.println("\t\t}");
			result.append("\t\t}\r\n");
			
			// 设置请求参数部分
			JSONObject json = new JSONObject(requestFieldString);
			List<String> maybePrintObject = new ArrayList<String>();
			Iterator iterator1 = json.keys();
			while(iterator1.hasNext()){
				String name = iterator1.next().toString();
				Object value = json.get(name);
				// 得到值的类型：String Double Integer JSONArray JSONObject
				String type = value.getClass().getSimpleName();
				if("Integer".equals(type)){
					System.out.println("\t\tInteger "+name+" = parameter.get"+firstToUp(name)+"();");
					result.append("\t\tInteger "+name+" = parameter.get"+firstToUp(name)+"();\r\n");
				}else if("String".equals(type)){
					System.out.println("\t\tString "+name+" = parameter.get"+firstToUp(name)+"();");
					result.append("\t\tString "+name+" = parameter.get"+firstToUp(name)+"();\r\n");
				}else if("Double".equals(type)){
					System.out.println("\t\tdouble "+name+" = parameter.get"+firstToUp(name)+"();");
					result.append("\t\tdouble "+name+" = parameter.get"+firstToUp(name)+"();\r\n");
				}
			}
			
			System.out.println("//\t\tuserid = \"jfHflV6mlFF.yHzIhuTWuy\"; // 测试用户\r\n");
			result.append("//\t\tuserid = \"jfHflV6mlFF.yHzIhuTWuy\"; // 测试用户\r\n");
			
			System.out.println("\t\ttry{\n");
			result.append("\t\ttry{\r\n");
			
			System.out.println("\t\t\t// 逻辑代码\n");
			result.append("\t\t\t// 逻辑代码\n\r\n");
			
			System.out.println("\t\t} catch (Exception e) {");
			result.append("\t\t} catch (Exception e) {\r\n");
			
			System.out.println("\t\t\te.printStackTrace();");
			result.append("\t\t\te.printStackTrace();\r\n");
			
			System.out.println("\t\t}");
			result.append("\t\t}\r\n");
			
			System.out.println("\t\treturn generator.toSuccess(parser, payload.toJSON());");
			result.append("\t\treturn generator.toSuccess(parser, payload.toJSON());\r\n");
			
			System.out.println("\t}");
			result.append("\t}\r\n");
			
			System.out.println("}");
			result.append("}\r\n");
			writefiletotxt(fileName, result.toString(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param fieldString 所有变量定义，格式：:{"a":1,"b":2,...}
	 * toJson和fromJson的输出
	 */
	public static void doCreateTwoJson(String fieldString,String baseActionName,String fileName){
		StringBuffer result = new StringBuffer("");
		
		/** 信息 **/
		System.out.println("/**\r\n");
		result.append("/**\r\n");
		
		System.out.println(" * @author 高攀\r\n");
		result.append(" * @author 高攀\r\n");
		
		System.out.println(" * @date "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\r\n");
		result.append(" * @date "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\r\n");
		
		System.out.println(" */\r\n");
		result.append(" */\r\n");
		
		/** 信息end **/
		
		System.out.println("public class "+fileName.replaceAll(".java", "")+" extends JSONSerializable {");
		result.append("public class "+fileName.replaceAll(".java", "")+" extends JSONSerializable {\r\n");
		
		try {
			JSONObject json = null;
			if(fieldString.trim().charAt(0)=='['){ // JsonArray
				JSONArray jsona = new JSONArray(fieldString);
				json = jsona.getJSONObject(0); // 得到第一个JsonObject
			}else {
				json = new JSONObject(fieldString);
			}
			List<String> maybePrintObject = new ArrayList<String>();
			// 得到所有的键
			Iterator iterator1 = json.keys();
			// 1、打印字段部分
			while(iterator1.hasNext()){
				// 得到字段名称
				String name = iterator1.next().toString();
				// 得到所有的值
				Object value = json.get(name);
				// 得到值的类型：String Double Integer JSONArray JSONObject
				String type = value.getClass().getSimpleName();
				if(("JSONObject").equals(type)){ // 如果是json对象 
					String jsonName = baseActionName+firstToUp(name.trim())+"Item";
					System.out.println("\tprivate "+jsonName+" "+name.trim()+" = null;");
					result.append("\tprivate "+jsonName+" "+name.trim()+" = null;\r\n");
					// 传过去3个值，通过#分隔
					maybePrintObject.add(String.valueOf(baseActionName+"#"+name.trim()+"#"+value));
				}else if("JSONArray".equals(type)){
					String jsonName = baseActionName+firstToUp(name.trim())+"Item";
					System.out.println("\tprivate ArrayList<"+jsonName.trim()+"> "+name.trim()+"List = null;");
					result.append("\tprivate ArrayList<"+jsonName.trim()+"> "+name.trim()+"List = null;\r\n");
					// 传过去3个值，通过#分隔
					maybePrintObject.add(String.valueOf(baseActionName+"#"+name.trim()+"#"+value));
				}else {
					System.out.println("\tprivate "+type.trim()+" "+name.trim()+" = null;");
					result.append("\tprivate "+type.trim()+" "+name.trim()+" = null;\r\n");
				}
				
			}
			
			// 1.1、构造方法
			System.out.println("\tpublic "+fileName.replaceAll(".java", "")+" (){");
			result.append("\tpublic "+fileName.replaceAll(".java", "")+" (){\r\n");
			Iterator iterator1_1 = json.keys();
			while(iterator1_1.hasNext()){
				// 得到字段名称
				String name = iterator1_1.next().toString();
				// 得到所有的值
				Object value = json.get(name);
				// 得到值的类型：String Double Integer
				String type = value.getClass().getSimpleName();
				// 查看构造方法里面需要初始化数据不
				if(("JSONObject").equals(type)){ // 如果是json对象 
					String jsonName = baseActionName+firstToUp(name.trim())+"Item";
					System.out.println("\t\t"+name+" = new "+jsonName+"();");
					result.append("\t\t"+name+" = new "+jsonName+"();\r\n");
				}else if("JSONArray".equals(type)){
					String jsonName = baseActionName+firstToUp(name.trim())+"Item";
					System.out.println("\t\t"+name.trim()+"List"+" = new ArrayList<"+jsonName+">();");
					result.append("\t\t"+name.trim()+"List"+" = new ArrayList<"+jsonName+">();\r\n");
				}
			}
			System.out.println("\t}");
			result.append("\t}\r\n");
			// 构造方法end
			
			
			// 2、打印fromJosn部分
			Iterator iterator2 = json.keys();
			System.out.println("\t@Override");
			result.append("\t@Override\r\n");
			
			System.out.println("\tpublic void fromJSON(JSONObject json) throws ProtocolInvalidMessageException,ProtocolMissingFieldException {");
			result.append("\tpublic void fromJSON(JSONObject json) throws ProtocolInvalidMessageException,ProtocolMissingFieldException {\r\n");
			
			while(iterator2.hasNext()){
				// 得到字段名称
				String name = iterator2.next().toString();
				// 得到所有的值
				Object value = json.get(name);
				// 得到值的类型：String Double Integer
				String type = value.getClass().getSimpleName();
				
				if(("JSONObject").equals(type)){ // 如果是json对象 
					System.out.println("\t\t"+name.trim()+".fromJSON(ProtocolFieldHelper.getOptionalJSONObjectField(json, \""+name.trim()+"\"));");
					result.append("\t\t"+name.trim()+".fromJSON(ProtocolFieldHelper.getOptionalJSONObjectField(json, \""+name.trim()+"\"));\r\n");
				}else if("JSONArray".equals(type)){
					String jsonName = baseActionName+firstToUp(name.trim())+"Item";
					System.out.println("\t\t"+name.trim()+"List"+" = (ArrayList<"+jsonName.trim()+">)ProtocolFieldHelper.getOptionalListField(json, \""+name.trim()+"List"+"\", "+jsonName.trim()+".class);");
					result.append("\t\t"+name.trim()+"List"+" = (ArrayList<"+jsonName.trim()+">)ProtocolFieldHelper.getOptionalListField(json, \""+name.trim()+"List"+"\", "+jsonName.trim()+".class);\r\n");
				}else {
					System.out.println("\t\t"+name+" = "+"ProtocolFieldHelper.getOptional"+type.trim()+"Field(json, \""+name.trim()+"\");");
					result.append("\t\t"+name+" = "+"ProtocolFieldHelper.getOptional"+type.trim()+"Field(json, \""+name.trim()+"\");\r\n");
				}
				
			}
			System.out.println("\t}");
			result.append("\t}\r\n");
			
			// 3、打印toJosn部分
			Iterator iterator3 = json.keys();
			System.out.println("\t@Override");
			result.append("\t@Override\r\n");
			
			System.out.println("\tpublic JSONObject toJSON() throws ProtocolMissingFieldException {");
			result.append("\tpublic JSONObject toJSON() throws ProtocolMissingFieldException {\r\n");
			
			System.out.println("\t\tJSONObject json = new JSONObject();");
			result.append("\t\tJSONObject json = new JSONObject();\r\n");
			
			while(iterator3.hasNext()){
				// 得到字段名称
				String name = iterator3.next().toString();
				// 得到所有的值
				Object value = json.get(name);
				// 得到值的类型：String Double Integer
				String type = value.getClass().getSimpleName();
				if(("JSONObject").equals(type)){ // 如果是json对象 
					System.out.println("\t\tProtocolFieldHelper.putOptionalField(json, \""+name.trim()+"\", "+name.trim()+".toJSON());");
					result.append("\t\tProtocolFieldHelper.putOptionalField(json, \""+name.trim()+"\", "+name.trim()+".toJSON());\r\n");
				}else if("JSONArray".equals(type)){
					String jsonName = baseActionName+firstToUp(name.trim())+"Item";
					System.out.println("\t\tProtocolFieldHelper.putOptionalListToField(json, \""+name.trim()+"List"+"\", "+name.trim()+"List"+", "+jsonName.trim()+".class);");
					result.append("\t\tProtocolFieldHelper.putOptionalListToField(json, \""+name.trim()+"List"+"\", "+name.trim()+"List"+", "+jsonName.trim()+".class);\r\n");
				}else {
					System.out.println("\t\tProtocolFieldHelper.putOptionalField(json, \""+name.trim()+"\", "+name.trim()+");");
					result.append("\t\tProtocolFieldHelper.putOptionalField(json, \""+name.trim()+"\", "+name.trim()+");\r\n");
				}
			}
			System.out.println("\t\treturn json;");
			result.append("\t\treturn json;\r\n");
			
			System.out.println("\t}");
			result.append("\t}\r\n");
			
			System.out.println("}"); // 结尾
			result.append("}\r\n");
			
			writefiletotxt(fileName, result.toString(), true);
			
			result = new StringBuffer("");
			
			// 打印其他派生出来的类
			if(maybePrintObject.size()>0){
				for (int i = 0; i < maybePrintObject.size(); i++) {
					System.out.println("。。。。。。。。。。。。生成关联类（"+(i+1)+"）。。。。。。。。。。。。。。。");
					String baseActionNameTemp = maybePrintObject.get(i).split("#")[0];
					String curItemName = maybePrintObject.get(i).split("#")[1];
					fileName = baseActionNameTemp+firstToUp(curItemName)+"Item"+".java";
					System.out.println(baseActionNameTemp+firstToUp(curItemName)+"Item");
					// 解析json值
					doCreateTwoJson(maybePrintObject.get(i).split("#")[2],baseActionNameTemp+firstToUp(curItemName)+"Item",fileName);
					System.out.println("。。。。。。。。。。。。。。。。。。。。。。。。。。。");
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 把首字母转化成大写输出
	public static String firstToUp(String resource){
		String first =  (resource.trim().charAt(0)+"").toUpperCase();
		String last = resource.substring(1, resource.length());
		return first+last;
	}
	
	// 写文件：生成 xxx.java 文件
	public static void writefiletotxt(String fileName,String result,boolean append){
		try {
			FileWriter fw = new FileWriter(new File(currentuserdesktop+fileName), append);
			fw.write(result);
			fw.flush();
			fw.close();
			System.out.println(fileName+"\n===========生成完毕===========");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 初始化注释
	public static void doInit(){
		// 读取txt不乱码的写法
		InputStreamReader readRequest = null;
		BufferedReader readerRequest = null;
		
		InputStreamReader readResponse = null;
		BufferedReader readerResponse = null;
		try {
			// 读取txt不乱码的写法
			readRequest = new InputStreamReader(new FileInputStream("C:\\Users\\liupei\\Desktop\\request.txt"),"gbk");
			readerRequest = new BufferedReader(readRequest);
			
			while(-1!=readerRequest.read()){
				String line = readerRequest.readLine();
				String line_noNotes = ""; // 不含注释的 "tvl_id":"",
				String field = ""; // 字段
				String fieldNote = ""; // 注释
				if(null==line || "".equals(line.trim()) || line.trim()==null || line.trim()==""){
					continue ;
				} else if(line.trim().equals("}") || line.trim().equals("{") || line.trim().equals("[") || line.trim().equals("]") || line.trim().equals("[{") || line.trim().equals("}]")){
					// 直接打印
					line_noNotes = line.trim();
				} else {
					if(line.contains("//")){ // 有注释
						line_noNotes = line.split("//")[0];
						String fieldTemp = line.split("//")[0];
						// 得到字段名
						fieldTemp = fieldTemp.split(":")[0]; // 得到 "tvl_id"
						fieldTemp = fieldTemp.replaceAll("\"", ""); // 去掉 ""
						field = fieldTemp;
						fieldNote = line.split("//")[1];
					}else {
						line_noNotes = line;
						String fieldTemp = line.split("//")[0];
						fieldTemp = fieldTemp.split(":")[0]; // 得到 "tvl_id"
						fieldTemp = fieldTemp.replaceAll("\"", ""); // 去掉 ""
						field = fieldTemp;
						fieldNote = "无";
					}
				}
				if(null!=field && !field.equals("")){
					mapRequest.put(field.trim(), fieldNote.trim());
				}
				resourceRequest.append(line_noNotes.trim()+"\n");
			}
//			System.out.println(mapRequest);
//			System.out.println(resourceRequest.toString());
			JSONObject jsonValidate = new JSONObject("{"+resourceRequest.toString()+"}"); // 校验得到的json数据
			
			/** 响应类  **/
			readResponse = new InputStreamReader(new FileInputStream("C:\\Users\\liupei\\Desktop\\response.txt"),"gbk");
			readerResponse = new BufferedReader(readResponse);
			
			while(-1!=readerResponse.read()){
				String line = readerResponse.readLine();
				String line_noNotes = ""; // 不含注释的 "tvl_id":"",
				String field = ""; // 字段
				String fieldNote = ""; // 注释
				if(null==line || "".equals(line.trim()) || line.trim()==null || line.trim()==""){
					continue ;
				} else if(line.trim().equals("}") || line.trim().equals("{") || line.trim().equals("[") || line.trim().equals("]") || line.trim().equals("[{") || line.trim().equals("}]")){
					// 直接打印
					line_noNotes = line.trim();
				} else {
					if(line.contains("//")){ // 有注释
						line_noNotes = line.split("//")[0];
						String fieldTemp = line.split("//")[0];
						// 得到字段名
						fieldTemp = fieldTemp.split(":")[0]; // 得到 "tvl_id"
						fieldTemp = fieldTemp.replaceAll("\"", ""); // 去掉 ""
						field = fieldTemp;
						fieldNote = line.split("//")[1];
					}else {
						line_noNotes = line;
						String fieldTemp = line.split("//")[0];
						fieldTemp = fieldTemp.split(":")[0]; // 得到 "tvl_id"
						fieldTemp = fieldTemp.replaceAll("\"", ""); // 去掉 ""
						field = fieldTemp;
						fieldNote = "无";
					}
				}
				if(null!=field && !field.equals("")){
					mapResponse.put(field.trim(), fieldNote.trim());
				}
				resourceResponse.append(line_noNotes.trim()+"\n");
			}
//			System.out.println(mapResponse);
//			System.out.println(resourceResponse.toString());
			JSONObject jsonValidate1 = new JSONObject("{"+resourceResponse.toString()+"}"); // 校验得到的json数据
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				readerRequest.close();
				readRequest.close();
				readerResponse.close();
				readResponse.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
