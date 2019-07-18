package com.theorydance.myspringboot.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix="other.basic")
@PropertySource("classpath:other.properties")
public class OtherProperties {
	private String title;
	private String blog;
}
