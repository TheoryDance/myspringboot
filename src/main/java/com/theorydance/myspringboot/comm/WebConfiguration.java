package com.theorydance.myspringboot.comm;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {
	
	@Bean
	public FilterRegistrationBean<Filter> testFilterRegistration(){
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new MyFilter());
		registration.addUrlPatterns("/*");
		registration.setName("MyFilter");
		// oder的值设置的越低，越优先执行
		registration.setOrder(6);
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean<Filter> testFilter2Registration(){
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new MyFilter2());
		registration.addUrlPatterns("/*");
		registration.setName("MyFilter2");
		// oder的值设置的越低，越优先执行
		registration.setOrder(2);
		return registration;
	}
	
//	@Bean
//	public FilterRegistrationBean<Filter> testUsernamePasswordAuthenticationFilter(){
//		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
//		registration.setFilter(new UsernamePasswordAuthenticationFilter());
//		registration.addUrlPatterns("/*");
//		registration.setName("MyUsernamePasswordAuthenticationFilter");
//		// oder的值设置的越低，越优先执行
//		registration.setOrder(1);
//		return registration;
//	}
	
}
