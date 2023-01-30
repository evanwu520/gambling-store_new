package com.ampletec.cloud.thrift.server.exception;

public class ThriftServerInstantiateException extends ThriftServerException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1437449413476185026L;

	public ThriftServerInstantiateException(String message) {
        super(message);
    }

    public ThriftServerInstantiateException(String message, Throwable t) {
        super(message, t);
    }
}
