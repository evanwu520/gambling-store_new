package com.ampletec.concurrent.taskchain;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class StorageStrategy {
    
	public abstract void saveContext(String groupKey, TaskContext taskContext);
    
	public abstract TaskContext getContext(String groupKey);
    
	public abstract void clean(String groupKey);
    
    protected TaskContext newContext() {
    	PriorityBlockingQueue<TaskObject> queue = new PriorityBlockingQueue<>();
        AtomicInteger index = new AtomicInteger();
        index.set(0);
        TaskContext ctx = new TaskContext(index, queue);
        return ctx;
    }
}
