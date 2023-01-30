package com.ampletec.cloud.security.apikey;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import com.ampletec.commons.encryption.APIKeyEncryptor;
import com.ampletec.cloud.security.AuthenticationResult;

public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
	
	public AuthenticationProvider(UserDetailsService userService, APIKEYSecurityProperties prop) {
		this.prop = prop;
		this.userService = userService;
	}
	
	private APIKEYSecurityProperties prop;
	
	private UserDetailsService userService;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Map<String, String> params = ((AuthenticationRequest)authentication).getRequestParams();
		UserDetails userDetails;
		try {
			String appid = params.get(prop.getIdKey());
			userDetails = userService.loadUserByUsername(appid);
			if(userDetails == null) {
				throw new InsufficientAuthenticationException("Appid Non-existent");
			}
			
			if(java.util.Objects.nonNull(prop.getExpires())) {
				if(params.containsKey(prop.getTimestampKey()) == false) {
					throw new NonceExpiredException("Signature expires");
				}
				long now = System.currentTimeMillis();
				String timestampStr = params.get(prop.getTimestampKey());
				long timestamp = Long.parseLong(timestampStr);
				if(timestampStr.length() < 11)
					timestamp = timestamp * 1000;
				if(now - timestamp > prop.getExpires().toMillis()) {
					throw new NonceExpiredException("Signature expires");
				}
			}
			
			String requestSignature = params.get(prop.getSignKey());
			params.remove(prop.getSignKey());
			String localSignature = APIKeyEncryptor.sign(params, prop.getSecretKey(), userDetails.getPassword());
			
			if (StringUtils.isEmpty(requestSignature) || !localSignature.equals(requestSignature.toUpperCase())) {
				throw new InsufficientAuthenticationException("Signature Authenticate Failed");
			}
		} catch(UsernameNotFoundException unfe) {
			throw new UsernameNotFoundException("Appid Non-existent");
		} catch(Exception e) {
			throw new NonceExpiredException(e.getMessage());
		}
		return new AuthenticationResult(userDetails);
	}

	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(AuthenticationRequest.class);
	}
}