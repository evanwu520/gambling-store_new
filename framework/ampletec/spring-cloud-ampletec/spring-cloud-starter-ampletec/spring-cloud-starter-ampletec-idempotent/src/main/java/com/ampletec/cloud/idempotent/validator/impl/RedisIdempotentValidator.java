package com.ampletec.cloud.idempotent.validator.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.ampletec.cloud.idempotent.annotation.Idempotent;
import com.ampletec.cloud.idempotent.validator.IdempotentValidator;
import com.ampletec.cloud.redis.RedisLockHelper;


public class RedisIdempotentValidator implements IdempotentValidator {
	
	public RedisIdempotentValidator(RedisLockHelper lockHelper) {
		this.lockHelper = lockHelper;
		this.spelParser = new SpelExpressionParser();
	}
	
	private final SpelExpressionParser spelParser;
	private final RedisLockHelper lockHelper;
	
    @Override
    public boolean validate(JoinPoint joinPoint, Idempotent validated) {
    	MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        List<String> paramNameList = Arrays.asList(methodSignature.getParameterNames());
        List<Object> paramList = Arrays.asList(joinPoint.getArgs());
    	
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        for (int i = 0; i < paramNameList.size(); i++) {
            ctx.setVariable(paramNameList.get(i), paramList.get(i));
        }
        ctx.setRootObject(methodSignature.getMethod());
        // 解析SpEL表达式获取结果
        String uniqueKey = spelParser.parseExpression(validated.expression()).getValue(ctx).toString();
        String value = UUID.randomUUID().toString();
        
        try {
        	return lockHelper.lock(uniqueKey, value, validated.expire(), validated.timeUnit());
        } finally {
        	lockHelper.unlock(uniqueKey, value);
        }
    }
}
