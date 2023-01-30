package com.ampletec.commons.messaging;

public abstract class EventHandler<T> {
	public abstract void onEvent(T msg) throws Exception;
}
