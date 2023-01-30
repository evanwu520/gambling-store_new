package com.ampletec.cloud.activity.service;

public interface ActivityService {
	public void startup();
	public void shutdown();
	default boolean isServing() {
		return false;
	}
}
