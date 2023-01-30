package com.ampletec.boot.redis.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.ampletec.boot.redis.RedisLock;

@Aspect
public class RedisLockAspect {
	
	private RedisLock redisLock;
	
	public RedisLockAspect(RedisLock redisLock) {
		this.redisLock = redisLock;
	}

	@Pointcut("@annotation(com.ampletec.boot.redis.annotation.RedisLock)")
    public void pointcut() {}
	
	@Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method signatureMethod = signature.getMethod();
        com.ampletec.boot.redis.annotation.RedisLock lock = signatureMethod.getAnnotation(com.ampletec.boot.redis.annotation.RedisLock.class);
        String key = lock.key();
        redisLock.lock(key);
        Object ret = joinPoint.proceed();
        redisLock.unlock(key);
        return ret;
	}
}
