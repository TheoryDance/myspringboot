package com.theorydance.myspringboot;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.theorydance.myspringboot.model.NeoProperties;
import com.theorydance.myspringboot.model.OtherProperties;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PropertiesTest {
	
	@Value("${neo.title}")
	private String othername;
	@Resource
	private NeoProperties neo;
	@Resource
	private OtherProperties other;
	
	@Test
	public void testSingle() {
		System.out.println(othername);
		System.out.println(neo);
		System.out.println(other);
	}
	
}
