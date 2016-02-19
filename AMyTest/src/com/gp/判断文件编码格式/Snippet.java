package com.gp.判断文件编码格式;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * @author 高攀
 * @下午5:28:26
 */

public class Snippet {
	
		/** 
		 * 判断文件的编码格式 
		 * @param fileName :文件全路径
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
}
