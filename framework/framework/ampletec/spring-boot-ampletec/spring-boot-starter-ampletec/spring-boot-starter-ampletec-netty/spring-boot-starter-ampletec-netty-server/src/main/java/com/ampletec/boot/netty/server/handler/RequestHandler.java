package com.ampletec.boot.netty.server.handler;

import io.netty.channel.Channel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.ampletec.commons.messaging.RequestMessage;
import com.ampletec.commons.net.PackageIn;

public abstract class RequestHandler<T extends RequestMessage> {	

	public RequestHandler() {}
	
	public void handleRequest(Channel channel, PackageIn in) throws Throwable {
		handleRequest(channel, transformMessage(in));
	}
	
	@SuppressWarnings("unchecked")
	protected final T transformMessage(PackageIn in) throws Throwable {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return (T) RequestMessage.newInstance((Class<RequestMessage>) params[0], in);
	}

	protected abstract void handleRequest(Channel channel, T oMessage) throws Throwable;
}
