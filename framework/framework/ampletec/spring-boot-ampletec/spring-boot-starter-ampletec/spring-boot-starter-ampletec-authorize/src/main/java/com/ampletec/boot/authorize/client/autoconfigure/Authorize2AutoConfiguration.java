package com.ampletec.boot.authorize.client.autoconfigure;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


@Configuration
@ComponentScan(basePackages = { "com.ampletec.boot.authorize.client" })
@EnableConfigurationProperties({TencentQQProperties.class, TencentWXProperties.class})
public class Authorize2AutoConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	public RestTemplate restTemplate() {
		RestTemplate template = new RestTemplate();
		SimpleClientHttpRequestFactory achrf = new SimpleClientHttpRequestFactory(); 
		achrf.setConnectTimeout(10000);
		achrf.setReadTimeout(10000);
		template.setRequestFactory(achrf);
		template.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8)); 
		return template;
	}
}
