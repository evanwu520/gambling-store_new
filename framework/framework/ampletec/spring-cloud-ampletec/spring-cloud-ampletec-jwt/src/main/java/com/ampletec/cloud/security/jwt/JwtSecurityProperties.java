package com.ampletec.cloud.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = JwtSecurityProperties.JWT_SECURITY_PREFIX)
public class JwtSecurityProperties {

	public static final String JWT_SECURITY_PREFIX = "security.jwt";
	
	private String header;
	private String tokenHead;	
	private int expires;
	private int refreshToken;	
	private String[] roles;
	private String[] permissives;
	
	@Value("${"+JwtSecurityProperties.JWT_SECURITY_PREFIX+".authentication.login}")
	private String loginUrl;
	@Value("${"+JwtSecurityProperties.JWT_SECURITY_PREFIX+".authentication.logout}")
	private String logoutUrl = "/logout";
	
	private String secret;
}
