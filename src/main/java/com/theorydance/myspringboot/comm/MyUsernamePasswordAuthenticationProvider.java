package com.theorydance.myspringboot.comm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MyUsernamePasswordAuthenticationProvider implements AuthenticationProvider{

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println(authentication.getClass().getName());
		System.out.println(authentication);
		System.out.println(authentication.getName());
		System.out.println(authentication.getPrincipal());
		System.out.println(authentication.getCredentials());
		
		MyUsernamePasswordAuthenticationToken token = new MyUsernamePasswordAuthenticationToken(null, (String)authentication.getPrincipal(), (String)authentication.getCredentials());
		String username = token.getUsername();
		String password = token.getPassword();
		System.out.println(username + "-" +password);
		List<GrantedAuthorityImpl> authorities = new ArrayList<>();
		authorities.add(new GrantedAuthorityImpl("user:add"));
		authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		// List<GrantedAuthority> list = AuthorityUtils.commaSeparatedStringToAuthorityList("admin:upd,ROLE_ADMIN");//设置权限和角色
	    // 1. commaSeparatedStringToAuthorityList放入角色时需要加前缀ROLE_，而在controller使用时不需要加ROLE_前缀
	    // 2. 放入的是权限时，不能加ROLE_前缀，hasAuthority与放入的权限名称对应即可
		
		token = new MyUsernamePasswordAuthenticationToken(authorities, token);
		SecurityContextHolder.getContext().setAuthentication(token);
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
