package com.ampletec.boot.web.exception;

public class RestException extends Exception {

	private static final long serialVersionUID = 1L;
 
	public static int OBJECT_NOT_FOUND = 404;
	
	private int code;

	public RestException(int code, String desc) {
		super(desc);
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}

	public String toString() {
		return "RestException, CODE=" + this.code;
	}
}
