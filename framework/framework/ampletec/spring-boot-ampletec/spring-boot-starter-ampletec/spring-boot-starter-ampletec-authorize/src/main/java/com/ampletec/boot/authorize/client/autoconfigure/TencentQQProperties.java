package com.ampletec.boot.authorize.client.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = TencentQQProperties.PREFIX)
public class TencentQQProperties {
	
	public static final String PREFIX = "ampletec.authorize.tencent-qq";

	private String authorizeRequest;
	
	private String tokenRequest;
	
	private String openidRequest;
	
	private String infoRequest;
}
