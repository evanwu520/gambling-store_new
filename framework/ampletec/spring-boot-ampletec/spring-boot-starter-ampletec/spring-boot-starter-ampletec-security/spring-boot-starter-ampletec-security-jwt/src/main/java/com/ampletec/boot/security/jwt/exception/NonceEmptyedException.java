package com.ampletec.boot.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class NonceEmptyedException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonceEmptyedException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a <code>NonceExpiredException</code> with the specified message and root
	 * cause.
	 *
	 * @param msg the detail message
	 * @param t root cause
	 */
	public NonceEmptyedException(String msg, Throwable t) {
		super(msg, t);
	}
}
