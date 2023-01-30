package com.ampletec.cloud.security.jwt;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import com.ampletec.cloud.security.AuthenticationResult;
import com.ampletec.cloud.security.jwt.exception.NonceEmptyedException;

public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
	
	public AuthenticationProvider(EncryptionStrategy<TokenDetails> jwtStrategy) {
		this.jwtStrategy = jwtStrategy;
	}
	
	private EncryptionStrategy<TokenDetails> jwtStrategy;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = ((AuthenticationRequest)authentication).getToken();
		
		if(StringUtils.isEmpty(token))
			throw new NonceEmptyedException("Token emptyed");
		TokenDetails tokenDetails;
		try {
			tokenDetails = jwtStrategy.decrypt(token);
		} catch(Exception e) {
			throw new NonceExpiredException("Token expires");
		}
		if(tokenDetails == null || tokenDetails.getExpiration().before(Calendar.getInstance().getTime()))
			throw new NonceExpiredException("Token expires: over time");

		return new AuthenticationResult(tokenDetails);
	}

	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(AuthenticationRequest.class);
	}
}
