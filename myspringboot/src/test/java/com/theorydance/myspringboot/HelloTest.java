package com.theorydance.myspringboot;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.theorydance.myspringboot.web.HelloController;
// 下面的方法是MockMvcResultHandlers的静态方法，这样写的话，在调用print()方法的时候，就不需要指定哪一个类了
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
public class HelloTest {
	// 进行web测试，内置很多工具类和方法，可以模拟post、get请求，print()打印执行结果
	private MockMvc mockMvc;
	
	@Test
	public void hello() {
		System.out.println("hello world");
	}
	// 该注解表示在测试启动的时候优先执行，一般用于资源初始化
	@Before
	public void setUp() throws Exception{
		mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
	}
	
	@Test
	public void getHello() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.post("/hello?name=小明").accept(MediaType.APPLICATION_JSON_UTF8))
			   .andDo(MockMvcResultHandlers.print());
		// print()方法会将请求和相应的过程都打印出来
	}
	
	@Test
	public void getHello2() throws Exception{
		// 说明，这样测试会通过，如果把最后面的那个“小明”改为“微笑”，则测试会不通过（断言）
		mockMvc.perform(MockMvcRequestBuilders.post("/hello?name=小明").accept(MediaType.APPLICATION_JSON_UTF8))
			   .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("小明")));
		// MockMvcResultMatchers.content()表示获取到web请求执行后的结果
		// Matchers.containsString("小明")，判断返回的结果集中是否包含“小明”这个字符串
	}
	
	
	
}
