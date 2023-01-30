package com.ampletec.boot.spring.messaging;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.fastjson.JSONObject;
import com.ampletec.boot.spring.messaging.annotation.Handler;
import com.ampletec.commons.lang.ActionHandler;
import com.ampletec.commons.messaging.Message;
import com.ampletec.commons.messaging.MessageHandler;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageAdapter implements ApplicationContextAware, SmartInitializingSingleton {
	
	protected ApplicationContext applicationContext;
	protected Map<Short, MessageHandler<Message>> messageMapping;	
	protected Map<String, ActionHandler<JSONObject>> actionMapping;
	
	public void onMessage(Message msg) throws Exception {
		MessageHandler<Message> handler = messageMapping.get(msg.getType());
		if (handler == null) {
			log.debug("MessageHandler({}) is not define", msg.getType());
		} else {
			handler.onMessage(msg);
		}
	}
	
	public void onProcess(JSONObject msg) throws Exception {
		ActionHandler<JSONObject> handler = actionMapping.get(msg.getString("action").toLowerCase());
		if (handler == null) {
			log.debug("ActionHandler({}) is not define", msg.getString("action"));
		} else {
			handler.onProcess(msg);
		}
	}
	
	@Override
	public void afterSingletonsInstantiated() {
		// TODO Auto-generated method stub
		this.actionMapping = Maps.newConcurrentMap();
		this.messageMapping = Maps.newConcurrentMap();
		Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(Handler.class);
		if (Objects.nonNull(beans)) {
            beans.forEach(this::registerHandler);
        }
	}
	
	@SuppressWarnings("unchecked")
	protected void registerHandler(String beanName, Object bean) {
    	Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);
    	
    	Handler annotation = clazz.getAnnotation(Handler.class);
    	if(annotation.value() != 0) {    		
        	if(!MessageHandler.class.isAssignableFrom(bean.getClass())) {
                throw new IllegalStateException(clazz + " is not instance of " + MessageHandler.class.getName());
            }
	    	MessageHandler<Message> handler = (MessageHandler<Message>) bean;
	    	messageMapping.put(annotation.value(), handler);
	        log.info("register type({}) ==> handler({})", annotation.value(), beanName);
    	} else if(StringUtils.isNotBlank(annotation.name())) {    		
        	this.registerHandler(annotation, beanName, bean);
    	}
	}
	
	@SuppressWarnings("unchecked")
	protected void registerHandler(Handler annotation, String beanName, Object bean) {
		ActionHandler<JSONObject> handler = (ActionHandler<JSONObject>) bean;
    	actionMapping.put(annotation.name().toLowerCase(), handler);
        log.info("register type({}) ==> handler({})", annotation.name(), beanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
}
