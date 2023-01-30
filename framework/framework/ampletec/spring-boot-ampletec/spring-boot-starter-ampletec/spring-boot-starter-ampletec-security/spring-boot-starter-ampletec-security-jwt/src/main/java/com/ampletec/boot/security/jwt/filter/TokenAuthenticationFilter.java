package com.ampletec.boot.security.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import com.ampletec.boot.security.jwt.AuthenticationRequest;
import com.ampletec.boot.security.jwt.EncryptionStrategy;
import com.ampletec.boot.security.jwt.JwtSecurityProperties;
import com.ampletec.boot.security.jwt.TokenDetails;
import com.ampletec.boot.security.jwt.handler.RefreshTokenHandler;
import com.ampletec.boot.security.filter.AuthenticationFilter;

public class TokenAuthenticationFilter extends AuthenticationFilter {

	private JwtSecurityProperties jwtProperties;
	
	public TokenAuthenticationFilter(EncryptionStrategy<TokenDetails> jwtStrategy, JwtSecurityProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
		this.successHandler = new RefreshTokenHandler(jwtStrategy, jwtProperties);
		this.requiresAuthenticationRequestMatcher = new RequestHeaderRequestMatcher(jwtProperties.getHeader());
	}
	
	@Override
	protected Authentication authenticate(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		String token = request.getHeader(jwtProperties.getHeader());
		if(StringUtils.isNotBlank(token)) {			
		    return this.getAuthenticationManager().authenticate(new AuthenticationRequest(token));
		} else {
			throw new InsufficientAuthenticationException("JWT is Empty");
		}
	}
}
