package com.ampletec.commons.lang;

public abstract class ActionHandler<T> {
	public abstract void onProcess(T msg) throws Exception;
}
