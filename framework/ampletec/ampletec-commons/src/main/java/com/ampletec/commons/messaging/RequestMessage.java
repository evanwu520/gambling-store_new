package com.ampletec.commons.messaging;

import com.ampletec.commons.net.PackageIn;

public abstract class RequestMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static RequestMessage newInstance(Class<RequestMessage> clazz, PackageIn in) throws Throwable {
		RequestMessage oMessage = null;
		try {
			oMessage = clazz.newInstance();
		} catch (Throwable e) {
			throw new RuntimeException("new instance of [" + clazz + "] failed :", e);
		}
		oMessage.setType(in.getType());
		oMessage.loadBytes(in);
		return oMessage;
	}

	public abstract void loadBytes(PackageIn in) throws Throwable;
}
