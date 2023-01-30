package com.ampletec.cloud.redis.autoconfigure;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.ampletec.cloud.redis.RedisLock;
import com.ampletec.cloud.redis.DistributedRedisLock;
import com.ampletec.cloud.redis.RedisLockHelper;
import com.ampletec.cloud.redis.aspect.DistributedRedisLockAspect;

@Configuration
public class ReidsLockAutoConfiguration {
	
	@Bean
	public RedisLock redislock(@Qualifier("redisTemplate") RedisTemplate<Object, Object> redisTemplate) {
		return new RedisLock(redisTemplate);
	}
 
    @Bean
    public DistributedRedisLock distributedRedisLock(@Qualifier("redisTemplate") RedisTemplate<Object, Object> redisTemplate){
        return new DistributedRedisLock(redisTemplate);
    }
 
    @Bean
    public DistributedRedisLockAspect distributedRedisLockAspect(DistributedRedisLock distributedRedisLock){
        return new DistributedRedisLockAspect(distributedRedisLock);
    }
    
    @Bean
    public RedisLockHelper lockHelper(StringRedisTemplate stringRedisTemplate, ScheduledExecutorService scheduledExecutor) {
    	return new RedisLockHelper(stringRedisTemplate, scheduledExecutor);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ScheduledExecutorService scheduledExecutor() {
    	return Executors.newScheduledThreadPool(10);
    }
}