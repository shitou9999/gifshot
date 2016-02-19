package com.gp.socket;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author 高攀
 * @下午2:47:08
 */
public class Client {
	
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.7", 1024);
			OutputStream outs = socket.getOutputStream();
			DataOutputStream dout = new DataOutputStream(outs);
			dout.writeUTF("哈哈asdasdsad@%#$@");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
