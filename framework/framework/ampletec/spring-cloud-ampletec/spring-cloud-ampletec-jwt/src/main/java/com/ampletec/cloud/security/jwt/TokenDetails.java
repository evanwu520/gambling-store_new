package com.ampletec.cloud.security.jwt;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.JwtBuilder;

public interface TokenDetails extends UserDetails {
	
	JwtBuilder builder();
	
	String getToken();
	
	String getIssuer();
	
	String getSubject();
	
	Date getIssuedAt();
	
	Date getExpiration();
}
