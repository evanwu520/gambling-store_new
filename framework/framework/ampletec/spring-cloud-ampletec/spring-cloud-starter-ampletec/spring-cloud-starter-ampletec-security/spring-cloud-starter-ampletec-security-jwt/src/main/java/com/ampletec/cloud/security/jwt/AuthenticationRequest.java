package com.ampletec.cloud.security.jwt;

import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationRequest extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 3981518947978158945L;
	
	private UserDetails principal;
	private String credentials;
	private String token;
	
	public AuthenticationRequest(String token) {
		super(Collections.emptyList());
		this.token = token;
	}

	public Object getCredentials() {
		return credentials;
	}

	public Object getPrincipal() {
		return principal;
	}
	
	public String getToken() {
		return token;
	}	
}
