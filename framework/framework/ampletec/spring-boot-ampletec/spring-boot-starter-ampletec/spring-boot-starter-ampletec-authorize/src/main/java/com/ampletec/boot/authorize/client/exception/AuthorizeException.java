package com.ampletec.boot.authorize.client.exception;

public class AuthorizeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthorizeException() {
		super();
	}
	
	public AuthorizeException(String message) {
		super(message);
	}
	
	public AuthorizeException(String message, Throwable cause) {
		super(message, cause);
	}
}
