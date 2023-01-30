package com.ampletec.cloud.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

public abstract class AuthenticationFilter extends OncePerRequestFilter{
	
	protected RequestMatcher requiresAuthenticationRequestMatcher;
	protected List<RequestMatcher> permissiveRequestMatchers;
	protected AuthenticationManager authenticationManager;
	

	protected AuthenticationSuccessHandler successHandler; //SavedRequestAwareAuthenticationSuccessHandler
	protected AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
	
	@Override
	public void afterPropertiesSet() {
		Assert.notNull(authenticationManager, "authenticationManager must be specified");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!requiresAuthentication(request, response)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		Authentication authResult = null;
		AuthenticationException failed = null;
		try {
			authResult = this.authenticate(request, response, filterChain);
		}catch (InternalAuthenticationServiceException e) {
			logger.error(
					"An internal error occurred while trying to authenticate the user.",
					failed);
			failed = e;
		}catch (AuthenticationException e) {
			// Authentication failed			
			failed = e;
		}
		if(authResult != null) {
		    successfulAuthentication(request, response, filterChain, authResult);
		} else if(!permissiveRequest(request)){
			unsuccessfulAuthentication(request, response, failed);
			return;
		}

		filterChain.doFilter(request, response);
	}
	
	protected abstract Authentication authenticate(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException;

	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		if(failureHandler != null) failureHandler.onAuthenticationFailure(request, response, failed);
	}
	
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, Authentication authResult) 
			throws IOException, ServletException{
		SecurityContextHolder.getContext().setAuthentication(authResult);
		if(successHandler != null) successHandler.onAuthenticationSuccess(request, response, authResult);
	}
	
	protected AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		return requiresAuthenticationRequestMatcher == null || requiresAuthenticationRequestMatcher.matches(request);
	}
	
	protected boolean permissiveRequest(HttpServletRequest request) {
		if(permissiveRequestMatchers == null)
			return false;
		for(RequestMatcher permissiveMatcher : permissiveRequestMatchers) {
			if(permissiveMatcher.matches(request))
				return true;
		}		
		return false;
	}
	
	public void setPermissiveUrl(String... urls) {
		if(permissiveRequestMatchers == null)
			permissiveRequestMatchers = new ArrayList<RequestMatcher>();
		for(String url : urls)
			permissiveRequestMatchers.add(new AntPathRequestMatcher(url));
	}
	
	public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
		Assert.notNull(successHandler, "successHandler cannot be null");
		this.successHandler = successHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
		Assert.notNull(failureHandler, "failureHandler cannot be null");
		this.failureHandler = failureHandler;
	}
}
