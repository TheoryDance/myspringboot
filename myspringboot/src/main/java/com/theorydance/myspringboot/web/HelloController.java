package com.theorydance.myspringboot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 该注解的含义是controller里面的方法都以JSON格式输出，不需要有其他额外的配置，如果注解为@Controller，代表输出内容到页面
// @RestController相当于@Controller+@ResponseBody合在一起的作用
@RestController
public class HelloController {

	@RequestMapping("/hello")
	public String hello(String name) {
		return "hello world, " + name;
	}
	
}
