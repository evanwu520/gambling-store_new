package com.ampletec.concurrent.taskchain;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskContext {

    public TaskContext(){}

    public TaskContext(AtomicInteger index, PriorityBlockingQueue<TaskObject> queue) {
        this.setIndex(index);
        this.setQueue(queue);
    }

    private AtomicInteger index;

    private PriorityBlockingQueue<TaskObject> queue;

	public AtomicInteger getIndex() {
		return index;
	}

	public void setIndex(AtomicInteger index) {
		this.index = index;
	}

	public PriorityBlockingQueue<TaskObject> getQueue() {
		return queue;
	}

	public void setQueue(PriorityBlockingQueue<TaskObject> queue) {
		this.queue = queue;
	}
	
	/**
     * if object can order return true, otherwise return false.
     *
     * @param object
     * @return
     */
    public boolean add(TaskObject object) {
        if(object.order()==0){
            return false;
        }
        this.queue.add(object);
        return true;
    }
    /**
     * poll an object which has order from the queue, if has not return null, else if has object has wrong
     * order, will return null until has right order object.
     *
     * @param groupKey
     * @return
     * @throws Exception
     */
    public TaskObject poll() throws Exception {
        if(this.queue.size()==0){
            return null;
        }
        int indexValue = this.index.intValue();
        TaskObject orderObject = queue.poll();
        if(orderObject==null) {
            return null;
        }
        int order = orderObject.order();
        if(order == indexValue){
            return orderObject;
        }else if(order == indexValue + 1){
        	this.index.incrementAndGet();
            return orderObject;
        }else {
            queue.add(orderObject);
            return null;
        }
    }
}
