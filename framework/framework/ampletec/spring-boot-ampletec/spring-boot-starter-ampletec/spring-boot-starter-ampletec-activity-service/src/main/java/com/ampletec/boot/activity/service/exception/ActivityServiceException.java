package com.ampletec.boot.activity.service.exception;

public class ActivityServiceException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActivityServiceException(String message) {
        super(message);
    }

    public ActivityServiceException(String message, Throwable t) {
        super(message, t);
    }
}