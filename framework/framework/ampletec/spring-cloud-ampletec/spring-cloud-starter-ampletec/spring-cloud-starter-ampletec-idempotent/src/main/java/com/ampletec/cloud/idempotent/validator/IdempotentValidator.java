package com.ampletec.cloud.idempotent.validator;

import org.aspectj.lang.JoinPoint;

import com.ampletec.cloud.idempotent.annotation.Idempotent;

public interface IdempotentValidator {

    /**
     *
     * @param joinPoint
     * @return
     */
    boolean validate(JoinPoint joinPoint, Idempotent validated);
}
