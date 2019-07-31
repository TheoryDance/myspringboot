package com.theorydance.myspringboot.model;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import net.sf.json.JSONObject;

@Data
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@NotEmpty(message="姓名不能为空")
	private String name;
	@Max(value=100, message="年龄不能大于100岁")
	@Min(value=18, message="必须年满18岁")
	private int age;
	@NotEmpty(message="密码不能为空")
	@Length(min=6,message="密码长度不得小于6位")
	private String pass;
	
	public String toString(){
		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("age", age);
		json.put("pass", pass);
		return json.toString();
	}
	
	public static User parseStr(String str){
		JSONObject json = JSONObject.fromObject(str);
		User user = new User();
		user.age = json.getInt("age");
		user.name = json.getString("name");
		user.pass = json.getString("pass");
		return user;
	}
}
