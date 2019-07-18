package com.theorydance.myspringboot.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix="neo")
public class NeoProperties {
	private String title;
	private String description;
}
