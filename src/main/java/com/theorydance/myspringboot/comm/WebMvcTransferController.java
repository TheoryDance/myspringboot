package com.theorydance.myspringboot.comm;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcTransferController extends WebMvcConfigurerAdapter{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/lucene/index").setViewName("lucene/index");
		registry.addViewController("/lucene/detail").setViewName("lucene/detail");
	}
	
}
