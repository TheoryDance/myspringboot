package com.theorydance.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.theorydance.myspringboot.datahandler.P1upgradeLogHandler;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		ApplicationContext application = SpringApplication.run(Application.class, args);
		P1upgradeLogHandler handler = application.getBean(P1upgradeLogHandler.class);
		try {
			handler.uploadLogsToES();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
