package com.theorydance.myspringboot.comm;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class GrantedAuthorityImpl implements GrantedAuthority{
	private static final long serialVersionUID = 1L;
	private String authority;
	public GrantedAuthorityImpl(){}
	public GrantedAuthorityImpl(String authority){
		this.authority = authority;
	}
}
