package com.theorydance.myspringboot.comm;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration {
	
	@Bean
	public FilterRegistrationBean<Filter> testFilterRegistration(){
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new MyFilter());
		registration.addUrlPatterns("/*");
		registration.setName("MyFilter");
		// order的值设置的越低，越优先执行
		registration.setOrder(6);
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean<Filter> testFilter2Registration(){
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new MyFilter2());
		registration.addUrlPatterns("/*");
		registration.setName("MyFilter2");
		// order的值设置的越低，越优先执行
		registration.setOrder(2);
		return registration;
	}
	
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addViewControllers(ViewControllerRegistry registry) {
				registry.addViewController("/lucene/index").setViewName("lucene/index");
				registry.addViewController("/lucene/detail").setViewName("lucene/detail");
			}
		};
	}
	
}
