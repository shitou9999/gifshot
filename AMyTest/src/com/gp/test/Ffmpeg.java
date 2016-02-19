package com.gp.test;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 高攀
 * @上午10:47:46
 * 生成视频缩略图
 */
public class Ffmpeg {

	public static void main(String[] args) {

		// 视频文件
		String videoRealPath = "D:"+File.separator+"ffmpeg\\mybat\\video\\test.mp4";
		// 截图的路径（输出路径）
		String imageRealPath = "D:\\ffmpeg\\mybat\\mytest\\test.jpg";

		// 方法三：调用系统中的可执行程序调用ffmpeg 提取视屏缩略图
		
		List<String> commend = new ArrayList<String>();
		commend.add("D:\\ffmpeg\\bin\\ffmpeg.exe");
		commend.add("-i");
		commend.add(videoRealPath);
		commend.add("-y");
		commend.add("-f");
		commend.add("image2");
		commend.add("-ss");
		commend.add("8");
		commend.add("-t");
		commend.add("2"); // 截图时间，暂不清楚单位是秒还是多少
		commend.add("-s");
		commend.add("200*500"); // 大小
		commend.add(imageRealPath);

		try {

			ProcessBuilder builder = new ProcessBuilder();

			builder.command(commend);

			builder.redirectErrorStream(true);

			System.out.println("视频截图开始...");

			// builder.start();

			Process process = builder.start();

			InputStream in = process.getInputStream();

			byte[] re = new byte[1024];

			System.out.print("正在进行截图，请稍候");

			while (in.read(re) != -1) {
				System.out.print(".");
			}
			System.out.println("");
			in.close();
			System.out.println("视频截图完成...");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("视频截图失败！");
		}
	}
}
