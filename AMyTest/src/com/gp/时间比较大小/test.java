package com.gp.时间比较大小;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @company keyhua
 * @author gpa
 */
public class test {
	
	
	public static void main(String[] args) {
			String startTime = "2015-02-06 12:19:50";
			String curTime = "2015-02-06 12:30:51";
			System.out.println(compareTime(curTime, startTime));
	}
	
	private static boolean compareTime(String curTime,String startTime){
		try {
			
			startTime = "2015-02-06 12:19:50";
			curTime = "2015-02-06 12:30:51";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = df.parse(startTime);
			Date date2 = df.parse(curTime);
			
			long time = date2.getTime()-date1.getTime();
			
			return time>0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
