package com.ampletec.boot.authorize.client.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = TencentWXProperties.PREFIX)
public class TencentWXProperties {
	
	public static final String PREFIX = "ampletec.authorize.tencent-wx";
	
	private String ticketRequest;

	private String authorizeRequest;
	
	private String tokenRequest;
	
	private String userRequest;
	
	private String openidRequest;
	
	private String infoRequest;

}
