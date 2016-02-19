package com.gp.反射bean和map的转换;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;

/**
 * 
 * @company keyhua
 * @author gpa
 */

public class Test {

	public static void main(String[] args) {

		/*People p = new People("aaa","男","2015-01-02",22,new Car("1561651", "2015-00-00", 12));

		try {

			// 把bean转换成map
			Map<String, Object> map = Test.coverBeanToMap(p);
			System.out.println(map);
			
			// 把map转换成bean
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("age", 12);
			map2.put("name", "hehe");
			map2.put("gender", "女");
			map2.put("car", new Car("carNum","carTime", 12));
			People p2 =  (People)Test.coverMapToBean(map2, People.class);
			System.out.println(p2);
			System.out.println(p2.getCar().getCarNum());
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		People A = new People("","男","2015-01-02",22,new Car("1561651", "2015-00-00", 12));
		PeopleB B = parseObjToObj(A, PeopleB.class);
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(B));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(JSON.toJSON(B));
	}

	/** 通过反射把类的转换成map集合 **/
	public static Map<String, Object> coverBeanToMap(Object obj)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> clazz = obj.getClass();
		Field field[] = clazz.getDeclaredFields();
		for (Field fieldd : field) {
			// 获得该类中的每一个字段名称
			String fieldName = fieldd.getName();
			// 构造对应字段的get方法名称
			String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1, fieldName.length());
			// 获得对应字段的get方法
			Method methodGet = clazz.getDeclaredMethod(methodName);
			// 执行get方法
			Object object = methodGet.invoke(obj);
			// 设置值
			map.put(fieldName, object);
		}
		return map;
	}
	
	/** 通过反射把map封装成类 **/
	public static Object coverMapToBean(Map<String, Object> map,Class<?> clazz) throws Exception{
		Object object = clazz.newInstance();
		Field[] filed = clazz.getDeclaredFields();
		for (Field field1 : filed) {
			Class<?> type = field1.getType();
			String fieldName = field1.getName();
			if(map.containsKey(fieldName)){
				Object value = map.get(fieldName);
				String setMethod = "set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1, fieldName.length());
				clazz.getMethod(setMethod, type).invoke(object,value);
			}
			
		}
		return object;
	}
	
	/** 通过把类A的相同字段转换类B对象
	 * @param <T>**/
	public static <T> T parseObjToObj(Object object,Class<T> clazz){
		try {
			Class in = object.getClass();
			T out = clazz.newInstance();
			Field[] fieldIn = in.getDeclaredFields();
			Field[] fieldOut = clazz.getDeclaredFields();
			for (Field fin : fieldIn) {
				fin.setAccessible(true); // 如果字段是私有(private)类型的，就需要设置这个属性
				String nameIn = fin.getName();
				Object valueIn = fin.get(object);
				String type = fin.getType().getSimpleName();
				for (Field fout : fieldOut) {
					fout.setAccessible(true);
					String nameOut = fout.getName();
					if(nameIn.equalsIgnoreCase(nameOut)){ // 字段相同
						if(null==valueIn){ // 如果值为空
							if("String".equals(type)){
								fout.set(out, "");
							}else if("Integer".equals(type)){
								fout.set(out, 0);
							}else if("Double".equals(type)){
								fout.set(out, 0.0);
							}else {
								fout.set(out, valueIn);
							}
						}else {
							fout.set(out, valueIn);
						}
					}
				}
			}
			return out;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
}





class People {
	private String name = null;
	private String gender = null;
	private String birthday = null;
	private Integer age = null;
	private Car car = null;

	public People() {
	}

	public People(String name, String gender, String birthday, Integer age,
			Car car) {
		super();
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.age = age;
		this.car = car;
	}

	public String getName() {
		return name;
	}

	public String print(String s) {
		return s + "===";
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public String toString() {
		return "People [name=" + name + ", gender=" + gender + ", birthday="
				+ birthday + ", age=" + age + ", car=" + car + "]";
	}

}
class PeopleB {
	private String name = null;
	private String gender = null;
	private String birthday = null;
	private int age;
	private Car car = null;
	
	public PeopleB() {
	}
	
	public PeopleB(String name, String gender, String birthday, Integer age,
			Car car) {
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.age = age;
		this.car = car;
	}
	
	public String getName() {
		return name;
	}
	
	public String print(String s) {
		return s + "===";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getBirthday() {
		return birthday;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Car getCar() {
		return car;
	}
	
	public void setCar(Car car) {
		this.car = car;
	}
	
	@Override
	public String toString() {
		return "People [name=" + name + ", gender=" + gender + ", birthday="
				+ birthday + ", age=" + age + ", car=" + car + "]";
	}
	
}

class Car {
	private String carNum = null;
	private String carTime = null;
	private Integer carAge = null;

	public Car(String carNum, String carTime, Integer carAge) {
		super();
		this.carNum = carNum;
		this.carTime = carTime;
		this.carAge = carAge;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getCarTime() {
		return carTime;
	}

	public void setCarTime(String carTime) {
		this.carTime = carTime;
	}

	public Integer getCarAge() {
		return carAge;
	}

	public void setCarAge(Integer carAge) {
		this.carAge = carAge;
	}

	@Override
	public String toString() {
		return "{\"carNum\":\"" + carNum + "\", \"carTime\":\"" + carTime
				+ "\", \"carAge\":\"" + carAge + "\"}";
	}
}