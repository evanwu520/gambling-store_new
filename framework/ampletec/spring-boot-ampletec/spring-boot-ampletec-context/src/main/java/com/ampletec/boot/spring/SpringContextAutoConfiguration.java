package com.ampletec.boot.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.ampletec.boot.spring.context.SpringContextProvider;

@Order(1)
@Configuration
public class SpringContextAutoConfiguration {

	@Bean("bootContextProvider")
	public SpringContextProvider contextProvider() {
		return new SpringContextProvider();
	}
}
