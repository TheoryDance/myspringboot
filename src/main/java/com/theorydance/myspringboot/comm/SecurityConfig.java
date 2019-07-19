package com.theorydance.myspringboot.comm;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// 说明：prePostEnabled默认为false，不开启的话，在方法上的@PreAuthorize无效
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Resource
	private MyUsernamePasswordAuthenticationProvider authenticationProvider;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/","/index","/resources/**","/static/**").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/content/**").access("hasRole('ADMIN') or hasRole('USER')")
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").permitAll()
			.and()
			.logout().permitAll()
			.and()
			.csrf().ignoringAntMatchers("/logout")
			.disable();
		http.addFilterAt(myAuthFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Bean
	public AuthenticationManager authenticationManager() throws Exception{
		return super.authenticationManagerBean();
	}
	
//	@Bean
	public MyUsernamePasswordAuthenticationFilter myAuthFilter(){
		MyUsernamePasswordAuthenticationFilter myf = new MyUsernamePasswordAuthenticationFilter();
		myf.setAuthenticationManager(authenticationManager);
		return myf;
	}
	
}
