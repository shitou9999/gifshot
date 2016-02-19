package com.gp.test;

import java.io.Serializable;

public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	private String stu_id;
	private String stu_name;

	public String getStu_id() {
		return stu_id;
	}

	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}

	public String getStu_name() {
		return stu_name;
	}

	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}

	@Override
	public String toString() {
		return "{\"stu_id\":\"" + stu_id + "\", \"stu_name\":\"" + stu_name
				+ "\"}";
	}
}