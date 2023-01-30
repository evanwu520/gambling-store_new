package com.ampletec.cloud.idempotent.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.ampletec.cloud.idempotent.annotation.Idempotent;
import com.ampletec.cloud.idempotent.validator.IdempotentValidator;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Aspect
public class IdempotentInterceptor {

	@Autowired
    public IdempotentInterceptor(IdempotentValidator validator) {
        this.validator = validator;
    }

    private final IdempotentValidator validator;
    
    @Around("@annotation(com.ampletec.cloud.idempotent.annotation.Idempotent) && @annotation(validated)")
    public void beforeAspect(ProceedingJoinPoint joinPoint, Idempotent validated) throws Throwable {
        if(validator.validate(joinPoint, validated)){
            joinPoint.proceed(joinPoint.getArgs());
        } else {
            log.debug("repeat invoke");
        }
    }
}
