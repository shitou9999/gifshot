package com.gp.解析蓝牙字节串;

import java.io.UnsupportedEncodingException;

/**
 * @author 高攀
 * @上午9:48:48
 */

public class Snippet {
	public static void main(String[] args) {
		byte[] a = { (byte) 0x31, (byte) 0x32, (byte) 0x61, (byte) 0xBF, (byte) 0xD5, (byte) 0xBF,
				(byte) 0xD5, (byte) 0xBF, (byte) 0xD5, (byte) 0xBF,
				(byte) 0xD5, (byte) 0xBF, (byte) 0xD5, (byte) 0xBF,
				(byte) 0xD5, (byte) 0xBF, (byte) 0xD5, (byte) 0xBF ,(byte) 0xD5,(byte) 0xBF};
		byte[] b = { (byte) 0xD5, (byte) 0xBF, (byte) 0xD5, (byte) 0xBF,
				(byte) 0xD5 };
		try {
			byte[] byte_3 = new byte[a.length + b.length];
			System.arraycopy(a, 0, byte_3, 0, a.length);
			System.arraycopy(b, 0, byte_3, a.length, b.length);
			System.out.println(new String(byte_3, "gb2312"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
