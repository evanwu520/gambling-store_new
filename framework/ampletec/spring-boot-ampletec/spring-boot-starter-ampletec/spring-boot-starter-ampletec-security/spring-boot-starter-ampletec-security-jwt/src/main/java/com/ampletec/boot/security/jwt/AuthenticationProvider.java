package com.ampletec.boot.security.jwt;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import com.ampletec.boot.security.jwt.exception.NonceEmptyedException;

import com.ampletec.boot.security.AuthenticationResult;

public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
	
	public AuthenticationProvider(EncryptionStrategy<TokenDetails> jwtStrategy) {
		this.jwtStrategy = jwtStrategy;
	}
	
	private EncryptionStrategy<TokenDetails> jwtStrategy;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = ((AuthenticationRequest)authentication).getToken();
		
		if(StringUtils.isEmpty(token)) {
			throw new NonceEmptyedException("Token emptyed");
		}
		TokenDetails tokenDetails;
		try {
			tokenDetails = jwtStrategy.decrypt(token);
		} catch(Exception e) {
			throw new NonceExpiredException("Token exception: decrypt failed");
		}
		if(tokenDetails == null || tokenDetails.getExpiration().before(Calendar.getInstance().getTime())) {
			throw new NonceExpiredException("Token expires: over time");
		}

		return new AuthenticationResult(tokenDetails);
	}

	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(AuthenticationRequest.class);
	}
}
