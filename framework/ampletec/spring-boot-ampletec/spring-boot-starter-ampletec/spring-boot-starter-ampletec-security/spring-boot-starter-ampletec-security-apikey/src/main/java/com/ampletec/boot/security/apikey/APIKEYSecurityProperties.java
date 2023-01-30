package com.ampletec.boot.security.apikey;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = APIKEYSecurityProperties.APIKEY_SECURITY_PREFIX)
public class APIKEYSecurityProperties {

	public static final String APIKEY_SECURITY_PREFIX = "security.apikey";
	
	private String idKey;
	private String signKey;
	private String secretKey;
	private String timestampKey;
	
	@DurationUnit(ChronoUnit.SECONDS)
	private Duration expires = Duration.ofSeconds(3);
}
