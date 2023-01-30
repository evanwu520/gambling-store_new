package com.ampletec.boot.security.apikey;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationRequest extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 3981518947978158945L;
	
	private UserDetails principal;
	private String credentials;
	private Map<String, String> params;
	
	public AuthenticationRequest(Map<String, String> params) {
		super(Collections.emptyList());
		this.params = params;
	}

	public Object getCredentials() {
		return credentials;
	}

	public Object getPrincipal() {
		return principal;
	}
	
	public Map<String, String> getRequestParams() {
		return params;
	}	
}
