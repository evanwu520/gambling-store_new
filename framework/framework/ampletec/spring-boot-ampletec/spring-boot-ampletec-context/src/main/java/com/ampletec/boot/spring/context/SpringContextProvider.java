package com.ampletec.boot.spring.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ampletec.commons.lang.Validator;

public class SpringContextProvider implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	@Override
	@Autowired
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		SpringContextProvider.applicationContext = applicationContext;
	}

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    @SuppressWarnings("unchecked")
	public static <T> T getBeanByAnnotation(Class<? extends Annotation> clazz, Validator<T> v) {
		Map<String, Object> map = SpringContextProvider.getBeansWithAnnotation(clazz);
		try {
			for (String key : map.keySet()) {
				T t = (T)SpringContextProvider.getTarget(map.get(key));
				if(v.validate(t)) {
					return t;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
    }
    
	public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> cls) {
		return getApplicationContext().getBeansWithAnnotation(cls);
	}    
    
	public static Object getTarget(Object proxy) throws Exception {

		if (!AopUtils.isAopProxy(proxy)) {
			return proxy;
		}

		if (AopUtils.isJdkDynamicProxy(proxy)) {
			return getJdkDynamicProxyTargetObject(proxy);
		} else { // cglib
			return getCglibProxyTargetObject(proxy);
		}

	}

	private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
		Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
		h.setAccessible(true);
		Object dynamicAdvisedInterceptor = h.get(proxy);

		Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
		advised.setAccessible(true);

		Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

		return target;
	}

	private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
		Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
		h.setAccessible(true);
		AopProxy aopProxy = (AopProxy) h.get(proxy);

		Field advised = aopProxy.getClass().getDeclaredField("advised");
		advised.setAccessible(true);

		Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();

		return target;
	}
}
