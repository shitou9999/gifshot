package com.gp.生成唯一;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 
 * @company keyhua
 * @author gpa
 */
class people {
	String name = null;
	String sex = null;
	Integer age = null;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "people [name=" + name + ", sex=" + sex + ", age=" + age + "]";
	}
			
}
public class test {

	public static void main(String[] args) {
		
		System.out.println(UniqueIDGenerator.generate().length());
		
	}
	
	public static Object coverMapToBean(Map<String,Object> map,Class<?> clazz) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		Object object = clazz.newInstance();
		Field[] field = clazz.getDeclaredFields();
		for (Field fieldd : field) {
			String fieldName = fieldd.getName();
			Class<?> type = fieldd.getType();
			String fieldName_getMethod = "set"+fieldName.substring(0, 1).toUpperCase()+fieldName.subSequence(1, fieldName.length());
			Method method = clazz.getMethod(fieldName_getMethod,type);
			if(null != map.get(fieldName)){
				Object value = map.get(fieldName);
				Object ob = method.invoke(object, value);
			}
		}
		return object;
	}
	
}
