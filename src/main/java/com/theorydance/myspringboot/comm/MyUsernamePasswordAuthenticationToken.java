package com.theorydance.myspringboot.comm;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MyUsernamePasswordAuthenticationToken extends AbstractAuthenticationToken{

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	public MyUsernamePasswordAuthenticationToken(Collection<? extends GrantedAuthority> authorities,String username,String password) {
		super(authorities);
		this.username = username;
		this.password = password;
	}
	public MyUsernamePasswordAuthenticationToken(Collection<? extends GrantedAuthority> authorities,MyUsernamePasswordAuthenticationToken token) {
		super(authorities);
		this.username = token.getUsername();
		this.password = token.getPassword();
	}
	
	@Override
	public Object getCredentials() {
		return this.password;
	}

	@Override
	public Object getPrincipal() {
		return this.username;
	}

}
