package com.gp.浮点和双精度;

import java.text.DecimalFormat;

/**
 * @author 高攀
 * @上午10:34:04
 */
public class FloatDoulbe {

	public static void main(String[] args) {
		// 小数的float转成double会有精度丢失的问题
		double dou = 0.01;
		float flo = 35.11f;
		double dou1 = dou;
		double dou2 = flo;
		double dou3 = flo * 1;
		System.out.println(flo);
		System.out.println(dou);
		System.out.println(dou1);
		System.out.println(dou2);
		System.out.println("应付款：" + flo + "实际付款" + dou3);
		DecimalFormat df = new DecimalFormat("######0.00");
		double d1 = 3.27000056;
		df.format(d1);// 返回的是String
		System.out.println(df.format(d1));
		d1 = Math.floor(d1*100)/100;
		System.out.println(Math.ceil(d1*100+.5));
//		d1 = Math.ceil(d1*100+.5)/100;
		System.out.println(d1);
	}

}
