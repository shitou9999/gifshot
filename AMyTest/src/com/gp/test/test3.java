package com.gp.test;

import java.util.Iterator;

import com.gp.json.JSONException;
import com.gp.json.JSONObject;


public class test3 {

	public static void main(String[] args) {
		try {
			JSONObject parm = new JSONObject("{\"a\":1,\"b\":\"2s\"}");
			Iterator it = parm.keys();
			while(it.hasNext()){
				Object obj = it.next();
				String key = obj+"";
				Object value = parm.get(key);
				System.out.println(key+":"+value);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
