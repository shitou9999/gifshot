package com.gp.汉字转拼音;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class getPinYin {

	// 得到拼音
	public static String getPinYin1(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0] + "";
				} else {
					t4 += Character.toString(t1[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t4;
	}

	// 只得到首字母的简写
	public static String getPinYinSimple(String src) {
		StringBuffer result = new StringBuffer("");
		for (int i = 0,j=1; i < src.length(); i++,j++) {
			String temp = getPinYin1(src.substring(i, j));
			if(temp.length()>0){
				temp = temp.substring(0, 1);
			}
			result.append(temp);
		}
		return result.toString();
	}
	public static void main(String[] args) {
		System.out.println(new getPinYin().getPinYin1("成都"));
		System.out.println(new getPinYin().getPinYinSimple("成都四川"));
	}
}
