package com.ampletec.cloud.redis.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.ampletec.cloud.redis.RedisLock;

@Aspect
public class RedisLockAspect {
	
	@Autowired
	private RedisLock redisLock;

	@Pointcut("@annotation(com.ampletec.cloud.redis.annotation.RedisLock)")
    public void pointcut() {}
	
	@Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method signatureMethod = signature.getMethod();
        com.ampletec.cloud.redis.annotation.RedisLock lock = signatureMethod.getAnnotation(com.ampletec.cloud.redis.annotation.RedisLock.class);
        String key = lock.key();
        redisLock.lock(key);
        Object ret = joinPoint.proceed();
        redisLock.unlock(key);
        return ret;
	}
}
