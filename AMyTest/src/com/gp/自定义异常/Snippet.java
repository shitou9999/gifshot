package com.gp.自定义异常;
/**
 * @author 高攀
 * @上午10:24:42
 */

public class Snippet {
	public static void main(String[] args) {
		try {
			int a = (int) Math.random()+1;
			if(a==1){
				throw new myE("席八");
			}else {
				throw new myEA("席八a");
			}
		} catch (myE e) {
			e.printStackTrace();
		} catch (myEA e) {
			e.printStackTrace();
		}
	}
}
class myE extends Exception{
	public myE() {
		
	}
	public myE(String msg) {
		super(msg);
	}
	
}
class myEA extends Exception{
	public myEA() {
		
	}
	public myEA(String msg) {
		super(msg);
	}
	
}