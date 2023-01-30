package com.ampletec.cloud.concurrent.taskchain.impl;

import java.time.Duration;
import java.util.Objects;

import org.springframework.data.redis.core.RedisTemplate;

import com.ampletec.concurrent.taskchain.StorageStrategy;
import com.ampletec.concurrent.taskchain.TaskContext;

@SuppressWarnings("unchecked")
public class RedisStrategy extends StorageStrategy {
	
	private final static String REDIS_TASKCHAIN_WOKERS = "taskchain.wokers:";
    
	public RedisStrategy(String application, RedisTemplate<?, ?> redisTemplate, Duration expire) {
    	this.groupBy = REDIS_TASKCHAIN_WOKERS + application + ":";
    	this.expire = expire;
    	this.redisTemplate = (RedisTemplate<Object, TaskContext>)redisTemplate;
    }

    private String groupBy;
    private Duration expire;
    private RedisTemplate<Object, TaskContext> redisTemplate;
    
    @Override
    public void saveContext(String groupKey, TaskContext taskContext) {
    	// TODO Auto-generated method stub
    	redisTemplate.opsForValue().set(this.groupBy + groupKey, taskContext, this.expire);
    }

    @Override
    public TaskContext getContext(String groupKey) {
    	// TODO Auto-generated method stub
    	TaskContext ctx = null;
    	if(redisTemplate.hasKey(this.groupBy + groupKey)) {
    		ctx = redisTemplate.opsForValue().get(this.groupBy + groupKey);
    	} 
    	if(Objects.isNull(ctx)) {
    		ctx = this.newContext();
			this.saveContext(groupKey, ctx);
    	}
		return ctx;
    }

	@Override
    public void clean(String groupKey) {
        redisTemplate.delete(this.groupBy + groupKey);
    }
}
