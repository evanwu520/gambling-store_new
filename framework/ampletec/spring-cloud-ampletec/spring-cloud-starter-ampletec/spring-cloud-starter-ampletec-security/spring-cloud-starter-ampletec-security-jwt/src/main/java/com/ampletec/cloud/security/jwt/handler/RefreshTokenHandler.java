package com.ampletec.cloud.security.jwt.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ampletec.cloud.security.jwt.EncryptionStrategy;
import com.ampletec.cloud.security.jwt.JwtSecurityProperties;
import com.ampletec.cloud.security.jwt.TokenDetails;
import com.ampletec.cloud.security.AuthenticationResult;

public class RefreshTokenHandler implements AuthenticationSuccessHandler {
	
	private JwtSecurityProperties jwtProperties;
	
	private EncryptionStrategy<TokenDetails> jwtStrategy;
	
	public RefreshTokenHandler(EncryptionStrategy<TokenDetails> jwtStrategy, JwtSecurityProperties jwtProperties) {
		this.jwtStrategy = jwtStrategy;
		this.jwtProperties = jwtProperties;
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) throws IOException, ServletException {
		AuthenticationResult authResult = (AuthenticationResult)auth;
		
		TokenDetails details = (TokenDetails)authResult.getDetails();
		boolean shouldRefresh = shouldTokenRefresh(details.getIssuedAt());
		if(shouldRefresh) {			
            String newToken = this.jwtStrategy.encrypt(details, this.jwtProperties.getExpires());
            response.setHeader(jwtProperties.getHeader(), newToken);
        }
	}
	
	protected boolean shouldTokenRefresh(Date issueAt){
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(jwtProperties.getRefreshToken()).isAfter(issueTime);
    }

}
