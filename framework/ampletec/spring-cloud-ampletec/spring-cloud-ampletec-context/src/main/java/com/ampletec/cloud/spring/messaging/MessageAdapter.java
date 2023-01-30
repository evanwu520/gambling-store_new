package com.ampletec.cloud.spring.messaging;

import java.util.Map;
import java.util.Objects;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ampletec.cloud.spring.messaging.annotation.Handler;
import com.ampletec.commons.messaging.Message;
import com.ampletec.commons.messaging.MessageHandler;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageAdapter implements ApplicationContextAware, SmartInitializingSingleton {
	
    private ApplicationContext applicationContext;
	private Map<Short, MessageHandler<Message>> mapping;
	
	public void onMessage(Message msg) throws Exception {
		MessageHandler<Message> handler = mapping.get(msg.getType());
		if (handler == null) {
			log.warn("MessageHandler({}) is not define", msg.getType());
		} else {
			handler.onMessage(msg);
		}
	}
	
	@Override
	public void afterSingletonsInstantiated() {
		// TODO Auto-generated method stub
		this.mapping = Maps.newConcurrentMap();
		Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(Handler.class);
		if (Objects.nonNull(beans)) {
            beans.forEach(this::registerHandler);
        }
	}
	
	@SuppressWarnings("unchecked")
	private void registerHandler(String beanName, Object bean) {
    	Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);
		
    	if(!MessageHandler.class.isAssignableFrom(bean.getClass())) {
            throw new IllegalStateException(clazz + " is not instance of " + MessageHandler.class.getName());
        }
    	
    	Handler annotation = clazz.getAnnotation(Handler.class);
    	MessageHandler<Message> handler = (MessageHandler<Message>) bean;
    	mapping.put(annotation.value(), handler);
        
        log.info("register type({}) ==> handler({})", annotation.value(), beanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
}
