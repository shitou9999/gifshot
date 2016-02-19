package com.gp.gifshot.myoverride;

import android.app.Application;

/**
 * @author 高攀
 * @下午1:54:49
 */
public class MyApplication extends Application{

	private boolean isMount = false; // sd卡是否挂载
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	public boolean isMount() {
		return isMount;
	}

	public void setMount(boolean isMount) {
		this.isMount = isMount;
	}
	
	
}
