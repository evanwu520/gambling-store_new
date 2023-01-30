package com.ampletec.commons.messaging;

public abstract class MessageHandler<T extends Message> {
	public abstract void onMessage(T msg) throws Exception;
}
