package com.ampletec.concurrent.taskchain.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ampletec.commons.lang.DateTime;
import com.ampletec.concurrent.taskchain.StorageStrategy;
import com.ampletec.concurrent.taskchain.TaskContext;

public class MemoryStrategy extends StorageStrategy {
	
	public MemoryStrategy(int expire) {
		MemoryStrategy strategy = this;
		scheduledThreadPool = Executors.newScheduledThreadPool(1);
		scheduledThreadPool.schedule(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				expireMap.forEach((k,v)->{
		            if((DateTime.now()-(expire*60))>v){
		            	strategy.clean(k);
		            }
		        });
			}
		}, 60, TimeUnit.SECONDS);
	}
	
	private ScheduledExecutorService scheduledThreadPool;
	private Map<String, TaskContext> orderObjectMap = new ConcurrentHashMap<>();
	private Map<String, Integer> expireMap = new ConcurrentHashMap<>();

	@Override
	public void saveContext(String groupKey, TaskContext taskContext) {
		// TODO Auto-generated method stub
		orderObjectMap.put(groupKey, taskContext);
        expireMap.put(groupKey, DateTime.now());
	}

	@Override
	public TaskContext getContext(String groupKey) {
		// TODO Auto-generated method stub
		if(orderObjectMap.containsKey(groupKey)) {
			return orderObjectMap.get(groupKey);
		} else {
			TaskContext ctx = this.newContext();
			this.saveContext(groupKey, ctx);
			return ctx;
		}
	}

	@Override
	public void clean(String groupKey) {
		// TODO Auto-generated method stub
		orderObjectMap.remove(groupKey);
        expireMap.remove(groupKey);
	}
}
