package com.gp.生成唯一;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author 高攀
 * @下午 4:03:48 生成用户账号的帮助类
 */
public class UserNameHelper {

	/**
	 * <pre>
	 * @param flag
	 *  	1：管理员a
	 *  	2：商家b 
	 *  	3：经纪人c
	 *  	4：司机d
	 *  	5：第三方机构
	 * </pre>
	 */
	public static String GenerateUserName(Integer flag) {
		StringBuffer buffer = new StringBuffer("");
		// part 1 【首字母】
		switch (flag) {
		case 1:
			buffer.append("a");
			break;
		case 2:
			buffer.append("b");
			break;
		case 3:
			buffer.append("c");
			break;
		case 4:
			buffer.append("d");
		case 5:
			buffer.append("e");
			break;
		default:
			return "";
		}
		// part 2 【A-Z中随机一个】
		buffer.append(getPart2());
		// part 3 【当前时间毫秒数后三位】
		buffer.append(getPart3());
		// part 4 【1-999 随机三位数】
		buffer.append(getPart4());
		return buffer.toString();
	}

	private static String getPart2() {
		return getRandomChar(1);
	}

	private static String getPart3() {
		Calendar calendar = Calendar.getInstance();
		calendar.getTime();
		return (calendar.getTimeInMillis() + "").substring(10);
	}

	private static String getPart4() {
		Random random = new Random();
		String base = random.nextInt(999)+"";
		switch (base.length()) {
		case 1:
			base = "00" + base;
			break;
		case 2:
			base = "0" + base;
			break;
		default:
			break;
		}
		return base;
	}

	/**
	 * JAVA获得A-Z范围的随机数
	 * 
	 * @param length
	 *            随机数长度
	 * @return String
	 */
	private static String getRandomChar(int length) {
		char[] chr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z' };
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(26)]);
		}
		return buffer.toString();
	}

	// 密码，随机几位数字
	public static String GeneratePassword(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(10)]);
		}
		return buffer.toString();
	}
	public static void main(String[] args) {
		try {
			/*List<String> contain = new ArrayList<String>();
			for (int i = 0; i < 10000; i++) {
				String get = UserNameHelper.GenerateUserName(2);
				if(contain.contains(get)){
					System.out.println(i);
					throw new Exception("重复！");
				}
				Thread.sleep(5);
				System.out.println(i);
				contain.add(get);
			}*/
			/*while(true){
				String get = UserNameHelper.GenerateUserName(2);
				if(contain.contains(get)){
					throw new Exception("重复！");
				}
				contain.add(get);
				Thread.sleep(5);
			}*/
			
//			UUID uuid = UUID.randomUUID();
//			System.out.println(uuid);
			System.out.println(UserNameHelper.GeneratePassword(6));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
