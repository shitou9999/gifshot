package com.gp.判断字符串的编码格式;


/**
 * @author 高攀
 * @下午5:30:05
 */

public class Snippet {

	public static void main(String[] args) {
		String string = "席八啊12";
		try {
			System.out.println("原来的编码是："+getEncoding(string));
			string = new String(string.getBytes("GB2312"),"unicode");
			System.out.println("转换为了utf-8编码："+getEncoding(string));
			System.out.println("字符串内容是："+string);
			System.out.println(new String(string.getBytes("unicode"),"GB2312"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}
}
