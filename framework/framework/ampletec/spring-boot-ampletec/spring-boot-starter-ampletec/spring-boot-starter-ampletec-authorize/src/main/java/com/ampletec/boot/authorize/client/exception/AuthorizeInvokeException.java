package com.ampletec.boot.authorize.client.exception;

public class AuthorizeInvokeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthorizeInvokeException(String message) {
		super(message);
	}

	
	public AuthorizeInvokeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AuthorizeInvokeException(int errCode, String info) {
		super(info);
		this.errCode = errCode;
	}
	

	private int errCode;
	public int getErrCode() {
		return this.errCode;
	}
}
