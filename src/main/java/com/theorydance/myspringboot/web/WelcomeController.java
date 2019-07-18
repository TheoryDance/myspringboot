package com.theorydance.myspringboot.web;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	@GetMapping("/")
	public String welcome(Map<String,Object> model){
		model.put("time", new Date());
		model.put("message", "hello, world!");
		System.out.println("this is welcome()");
		return "welcome";
	}

}
