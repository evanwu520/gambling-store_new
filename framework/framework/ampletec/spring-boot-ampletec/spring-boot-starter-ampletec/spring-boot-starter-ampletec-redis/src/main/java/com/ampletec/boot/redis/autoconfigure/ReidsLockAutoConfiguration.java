package com.ampletec.boot.redis.autoconfigure;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.ampletec.boot.redis.DistributedRedisLock;
import com.ampletec.boot.redis.RedisLock;
import com.ampletec.boot.redis.RedisLockHelper;
import com.ampletec.boot.redis.aspect.DistributedRedisLockAspect;
import com.ampletec.boot.redis.aspect.RedisLockAspect;

@Configuration("BootReidsLockAutoConfiguration")
public class ReidsLockAutoConfiguration {
	
	@Bean("bootRedisLock")
	public RedisLock redislock(@Qualifier("redisTemplate") RedisTemplate<Object, Object> redisTemplate) {
		return new RedisLock(redisTemplate);
	}
 
    @Bean
    public RedisLockAspect redisLockAspect(@Qualifier("bootRedisLock") RedisLock redisLock){
        return new RedisLockAspect(redisLock);
    }
 
    @Bean
    public DistributedRedisLock distributedRedisLock(@Qualifier("redisTemplate") RedisTemplate<Object, Object> redisTemplate){
        return new DistributedRedisLock(redisTemplate);
    }
 
    @Bean
    public DistributedRedisLockAspect distributedRedisLockAspect(DistributedRedisLock distributedRedisLock){
        return new DistributedRedisLockAspect(distributedRedisLock);
    }
    
    @Bean("bootRedisLockHelper")
    public RedisLockHelper lockHelper(StringRedisTemplate stringRedisTemplate, ScheduledExecutorService scheduledExecutor) {
    	return new RedisLockHelper(stringRedisTemplate, scheduledExecutor);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ScheduledExecutorService scheduledExecutor() {
    	return Executors.newScheduledThreadPool(10);
    }
}