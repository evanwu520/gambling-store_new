package com.ampletec.concurrent.taskchain;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

@SuppressWarnings("unchecked")
public class TaskChainWorker<T extends TaskObject>{
	
	public TaskChainWorker(StorageStrategy strategy, ChainExecutor<T> executor) {
		this.executor = executor;
		this.strategy = strategy;
	}
	
	private ChainExecutor<T> executor;
	private StorageStrategy strategy;
	
	public void execute(TaskObject object) {
		executor.execute((T)object);
	}

	public void prepare(TaskObject object) {
    	Set<TaskObject> tasks;
    	TaskContext ctx = this.strategy.getContext(object.groupKey());
    	boolean flag = ctx.add(object);
        if(!flag){
        	Set<TaskObject> array = new HashSet<>();
            array.add(object);
            tasks = array;
        } else {
        	tasks = polls(ctx);
        }
    	this.strategy.saveContext(object.groupKey(), ctx);
        if(!CollectionUtils.isEmpty(tasks)){
			tasks.forEach(t -> {
				executor.execute((T)t);
			});
		}
    }

	private Set<TaskObject> polls(TaskContext ctx) {
        Set<TaskObject> array = new HashSet<>();
        while (true){
        	TaskObject o = null;
            try {
                o = ctx.poll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(o==null){
                break;
            }
            if(o.isEndOrder()){
            	this.strategy.clean(o.groupKey());
            }
            array.add(o);
        }
        return array;
    }
}
