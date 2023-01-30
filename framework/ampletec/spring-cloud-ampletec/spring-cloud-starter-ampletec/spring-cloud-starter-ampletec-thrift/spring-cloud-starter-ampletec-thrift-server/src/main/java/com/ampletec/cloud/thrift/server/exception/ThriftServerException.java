package com.ampletec.cloud.thrift.server.exception;


public class ThriftServerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2387792340176286465L;

	public ThriftServerException(String message) {
        super(message);
    }

    public ThriftServerException(String message, Throwable t) {
        super(message, t);
    }
}
