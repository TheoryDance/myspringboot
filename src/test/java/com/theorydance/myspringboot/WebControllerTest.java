package com.theorydance.myspringboot;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.theorydance.myspringboot.web.WebController;

@SpringBootTest
public class WebControllerTest {

	// 进行web测试，内置很多工具类和方法，可以模拟post、get请求，print()打印执行结果
	private MockMvc mockMvc;
	
	// 该注解表示在测试启动的时候优先执行，一般用于资源初始化
	@Before
	public void setUp() throws Exception{
		mockMvc = MockMvcBuilders.standaloneSetup(new WebController()).build();
	}
	
	@Test
	public void getUser() throws Exception{
		// mockMvc.perform(MockMvcRequestBuilders.post("/getUser")).andDo(MockMvcResultHandlers.print());
		String responseString = mockMvc.perform(MockMvcRequestBuilders.post("/getUser"))
			   .andReturn().getResponse().getContentAsString();
		System.out.println(responseString);
	}
	
	@Test
	public void getUser2() throws Exception{
		// mockMvc.perform(MockMvcRequestBuilders.post("/getUser")).andDo(MockMvcResultHandlers.print());
		String responseString = mockMvc.perform(MockMvcRequestBuilders.post("/getUser2?name=ranfs&age=100"))
				.andReturn().getResponse().getContentAsString();
		System.out.println(responseString);
	}
	
	@Test
	public void getName() throws Exception{
		// mockMvc.perform(MockMvcRequestBuilders.post("/getUser")).andDo(MockMvcResultHandlers.print());
		String responseString = mockMvc.perform(MockMvcRequestBuilders.post("/getName/ranfusheng"))
				.andReturn().getResponse().getContentAsString();
		System.out.println(responseString);
	}
	
	@Test
	public void getUsers() throws Exception{
		String responseString = mockMvc.perform(MockMvcRequestBuilders.post("/getUsers"))
				.andReturn().getResponse().getContentAsString();
		System.out.println(responseString);
	}
	
	@Test
	public void saveUsers() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.post("/saveUser")
				.param("name", "")
				.param("age", "666")
				.param("pass", "test")
				);
	}
	
}
