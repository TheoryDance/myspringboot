package com.theorydance.myspringboot.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.theorydance.myspringboot.model.User;

@RestController
public class WebController {

	// @Valid表示该参数使用了参数校验，BindingResult参数校验的结果会存储在此对象中
	// @Valid属于Hibernate Validator
	@RequestMapping("/saveUser")
	public void saveUser(@Valid User user,BindingResult result) {
		System.out.println("user:" + user);
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error.getCode()+"-"+error.getDefaultMessage());
				
			}
		}
	}
	
	@RequestMapping(name="/getUser",value="/getUser",method=RequestMethod.POST)
	public User getUser() {
		User user = new User();
		user.setName("小明");
		user.setAge(12);
		user.setPass("123456");
		return user;
	}
	
	@RequestMapping(name="/getUser2",value="/getUser2",method=RequestMethod.POST)
	public User getUser2(User user) {
		return user;
	}
	
	@RequestMapping(value="/getName/{name}",method=RequestMethod.POST)
	public String getUser3(@PathVariable String name) {
		return name;
	}
	
	
	@RequestMapping(name="/getUsers",value="/getUsers",method= {RequestMethod.POST,RequestMethod.GET})
	public List<User> getUsers() {
		List<User> list = new ArrayList<>();
		User user = new User();
		user.setName("小明");
		user.setAge(12);
		user.setPass("123456");
		list.add(user);
		
		user = new User();
		user.setName("ranfs");
		user.setAge(100);
		user.setPass("123456");
		list.add(user);
		return list;
	}
	
}
