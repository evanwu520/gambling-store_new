package com.ampletec.cloud.spring.messaging.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;

@Target({ ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface Handler {

//	@AliasFor("protocol")
	short value() default 0;
	
//	@AliasFor("value")
//	short protocol() default 0;
}
