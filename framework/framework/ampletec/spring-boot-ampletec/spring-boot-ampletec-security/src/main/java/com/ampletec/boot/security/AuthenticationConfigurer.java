package com.ampletec.boot.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.ampletec.boot.security.filter.AuthenticationFilter;
import com.ampletec.boot.security.handler.AuthenticationFailureHandler;

public class AuthenticationConfigurer<T extends AuthenticationConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {
    
	private AuthenticationFilter authFilter;
	
	public AuthenticationConfigurer(AuthenticationFilter authFilter) {
		this.authFilter = authFilter;
	}
	
	@Override
	public void configure(B http) throws Exception {
		authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		authFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler());

		AuthenticationFilter filter = postProcess(authFilter);
		http.addFilterBefore(filter, LogoutFilter.class);
	}
	
	public AuthenticationConfigurer<T, B> permissiveRequestUrls(String ... urls){
		authFilter.setPermissiveUrl(urls);
		return this;
	}
}
