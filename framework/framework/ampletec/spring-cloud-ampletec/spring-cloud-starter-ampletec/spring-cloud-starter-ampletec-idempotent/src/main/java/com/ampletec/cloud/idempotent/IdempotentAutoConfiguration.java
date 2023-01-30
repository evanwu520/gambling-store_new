package com.ampletec.cloud.idempotent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.ampletec.cloud.idempotent.interceptor.IdempotentInterceptor;
import com.ampletec.cloud.idempotent.validator.IdempotentValidator;
import com.ampletec.cloud.idempotent.validator.impl.RedisIdempotentValidator;
import com.ampletec.cloud.redis.RedisLockHelper;

@Configuration
public class IdempotentAutoConfiguration {

	@Bean
	public IdempotentValidator idempotentValidator(RedisLockHelper lockHelper) {
		return new RedisIdempotentValidator(lockHelper);
	}
	
	@Bean
	@Order(1)
	public IdempotentInterceptor idempotentInterceptor(IdempotentValidator validator) {
		return new IdempotentInterceptor(validator);
	}
}
