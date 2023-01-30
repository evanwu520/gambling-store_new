package com.ampletec.cloud.activity.service.exception;

public class ActivityServiceInstantiateException extends ActivityServiceException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActivityServiceInstantiateException(String message) {
        super(message);
    }

    public ActivityServiceInstantiateException(String message, Throwable t) {
        super(message, t);
    }
}
