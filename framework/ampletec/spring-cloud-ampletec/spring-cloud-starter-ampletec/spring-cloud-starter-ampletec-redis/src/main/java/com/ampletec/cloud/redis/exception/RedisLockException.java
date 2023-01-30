package com.ampletec.cloud.redis.exception;

public class RedisLockException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RedisLockException(String message) {
		super(message);
	}

}
