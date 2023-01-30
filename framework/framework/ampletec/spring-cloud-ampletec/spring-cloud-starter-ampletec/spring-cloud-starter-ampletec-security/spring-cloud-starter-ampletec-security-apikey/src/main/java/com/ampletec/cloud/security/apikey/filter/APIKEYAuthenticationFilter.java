package com.ampletec.cloud.security.apikey.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import com.ampletec.cloud.security.apikey.APIKEYSecurityProperties;
import com.ampletec.cloud.security.apikey.AuthenticationRequest;
import com.ampletec.cloud.security.filter.AuthenticationFilter;
import com.ampletec.servlet.http.HttpServletUtils;

public class APIKEYAuthenticationFilter extends AuthenticationFilter {
	
	private APIKEYSecurityProperties apikeyProperties;

	public APIKEYAuthenticationFilter(APIKEYSecurityProperties apikeyProperties) {
		this.apikeyProperties = apikeyProperties;
	}

	@Override
	protected Authentication authenticate(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String, String> params = HttpServletUtils.getRequestParams(request);
		if(params.containsKey(apikeyProperties.getIdKey()) && params.containsKey(apikeyProperties.getSignKey())) {
			return this.getAuthenticationManager().authenticate(new AuthenticationRequest(params));
		} else {
			throw new InsufficientAuthenticationException("APPID or Signature is Empty");
		}
	}
}
