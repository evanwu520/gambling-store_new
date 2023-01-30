package com.ampletec.cloud.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.ampletec.cloud.spring.context.SpringContextProvider;

@Order(1)
@Configuration
public class SpringContextAutoConfiguration {

	@Bean
	public SpringContextProvider contextProvider() {
		return new SpringContextProvider();
	}
}
