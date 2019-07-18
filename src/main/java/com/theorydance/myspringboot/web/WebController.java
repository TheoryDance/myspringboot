package com.theorydance.myspringboot.web;

import java.util.Date;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
	
	// 不引入thymeleaf的时候，可以访问该路径，对应的的视图/WEB-INF/jsp/welcome.jsp
	// 引入thymeleaf后，访问路径变为了classpath:/templates/welcome.html
	@GetMapping("/welcomeJSP")
	public String welcomeJSP(Map<String,Object> model){
		model.put("time", new Date());
		model.put("message", "hello, world!");
		System.out.println("this is welcome()");
		return "welcome";
	}
	
	@RequestMapping("/thymeleafPage")
	public String thymeleafPage(ModelMap map){
		map.addAttribute("message", "http://www.ityouknow.com");
		return "thymeleafPage";
	}
	
	@RequestMapping("/login")
	public String login(ModelMap map){
		return "login";
	}
	
	@RequestMapping("/content")
	public String content(){
		return "content";
	}
	
	@RequestMapping({"/index","/"})
	public String index(){
		return "index";
	}
	
	@RequestMapping("/admin")
	public String admin(){
		return "admin";
	}
	
	@RequestMapping("/admin2")
	public String admin2(){
		return "admin";
	}
	
	@RequestMapping("/admin3")
	@PreAuthorize("hasAuthority('admin:upd')")
	public String admin3(){
		return "admin";
	}
	@RequestMapping("/admin4")
	@PreAuthorize("hasRole('ADMIN')")
	public String admin4(){
		return "admin";
	}
}
