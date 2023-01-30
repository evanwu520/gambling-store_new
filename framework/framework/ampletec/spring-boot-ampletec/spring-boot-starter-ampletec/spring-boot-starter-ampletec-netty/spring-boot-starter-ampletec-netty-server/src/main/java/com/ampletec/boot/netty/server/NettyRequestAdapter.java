package com.ampletec.boot.netty.server;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ampletec.boot.netty.server.annotation.NettyHandler;
import com.ampletec.boot.netty.server.handler.RequestHandler;
import com.ampletec.commons.messaging.RequestMessage;
import com.ampletec.commons.net.PackageIn;
import com.google.common.collect.Maps;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class NettyRequestAdapter implements ApplicationContextAware, SmartInitializingSingleton {
	
	public NettyRequestAdapter() {}
	
    private ApplicationContext applicationContext;
	private Map<Short, RequestHandler<RequestMessage>> mapping;
	
	public void handleRequest(Channel channel, PackageIn in) throws Throwable {
		short type = in.getType();
		RequestHandler<RequestMessage> handler = getRequestHandler(type);
		if (handler == null) {
			log.warn("RequestHandler({}) is not define", type);
		} else {
			try {
				handler.handleRequest(channel, in);
			} catch(Exception ex) {
				log.error(ExceptionUtils.getStackTrace(ex));
				this.exceptionCaught(ex, channel, in);
			}
		}
	}
	
	protected void exceptionCaught(Exception ex, Channel channel, PackageIn in) throws Exception {
		
	}

	@Override
	public void afterSingletonsInstantiated() {
		// TODO Auto-generated method stub
		
		this.mapping = Maps.newConcurrentMap();
		Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(NettyHandler.class);
		if (Objects.nonNull(beans)) {
            beans.forEach(this::registerHandler);
        }
	}
	
	@SuppressWarnings("unchecked")
	private void registerHandler(String beanName, Object bean) {
    	Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);
		
    	if(!RequestHandler.class.isAssignableFrom(bean.getClass())) {
            throw new IllegalStateException(clazz + " is not instance of " + RequestHandler.class.getName());
        }
    	
    	NettyHandler annotation = clazz.getAnnotation(NettyHandler.class);
    	RequestHandler<RequestMessage> handler = (RequestHandler<RequestMessage>) bean;
    	mapping.put(annotation.value(), handler);
        
        log.info("registerMapping code({}) ==> handler({})", annotation.value(), beanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}

	public RequestHandler<RequestMessage> getRequestHandler(short msgcode) {
		return mapping.get(msgcode);
	}
	
	public abstract void handleClose(Channel channel) throws Exception;
}
