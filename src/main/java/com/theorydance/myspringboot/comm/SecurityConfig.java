package com.theorydance.myspringboot.comm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/","/index","/resources/**").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/content/**").access("hasRole('ADMIN') or hasRole('USER')")
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").permitAll()
			.and()
			.logout().permitAll()
			.and()
			.csrf().ignoringAntMatchers("/logout");
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
			.passwordEncoder(new BCryptPasswordEncoder())
			// 这里设置用户名和密码后，会使得application.yml中的用户名配置不生效
			.withUser("user").password(new BCryptPasswordEncoder().encode("123456")).authorities("user:add,user:upd").roles("USER")
			.and()
			.withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).authorities("admin:add,admin:upd").roles("ADMIN");
	}
	
}
