package com.gp.socket;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 高攀
 * @下午2:47:17
 */
public class Server {
	
	public static void main(String[] args) {
		
		try {
			ServerSocket socket = new ServerSocket(1024);
			Socket socketGet = socket.accept();
			InputStream ins = socketGet.getInputStream();
			DataInputStream din = new DataInputStream(ins);
			String s = din.readUTF();
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
