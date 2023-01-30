package com.ampletec.cloud.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationResult extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 3981518947978158945L;
	
	private UserDetails principal;
	private String credentials;
	
	public AuthenticationResult(UserDetails principal) {
		super(principal.getAuthorities());
		// TODO Auto-generated constructor stub
		this.principal = principal;
		this.credentials = principal.getPassword();
		this.setDetails(principal);
		this.setAuthenticated(true);
	}

	public Object getCredentials() {
		return credentials;
	}

	public Object getPrincipal() {
		return principal;
	}	
}
