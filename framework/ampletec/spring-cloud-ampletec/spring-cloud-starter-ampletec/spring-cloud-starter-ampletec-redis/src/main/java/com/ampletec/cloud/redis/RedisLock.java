package com.ampletec.cloud.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisLock {

	public static final long TEN_SECONDS = 10 * 1000;
	public static final long TWO_SECONDS = 2 * 1000;
	
	public static final String REDIS_LOCK_PREFIX = "lock:";

    private static final int DEFAULT_ACQUIRY_RETRY_MILLIS = 100;

    private final int expireMsecs = 30 * 1000;
    
    public RedisLock(RedisTemplate<Object, Object> redisTemplate) {
    	this.redisTemplate = redisTemplate;
    }

    private RedisTemplate<Object, Object> redisTemplate;
	
	private boolean setNX(final String key, final long value) {
		boolean ret = redisTemplate.opsForValue().setIfAbsent(key, value);
        if(ret) redisTemplate.expire(key, value, TimeUnit.SECONDS);
        return ret;
    }
	
    private long getSet(final String key, final long value) {    	
        Object obj = redisTemplate.opsForValue().getAndSet(key, value);
        return obj != null ? (long) obj : null;
    }
	
	private long getExpires(final String key) {
        Object obj = redisTemplate.opsForValue().get(key);
        return obj != null ? (long)obj : 0;
    }
	
	public synchronized boolean lock(String key) throws Exception {
		return lock(key, 0);
	}

	public synchronized boolean lock(String key, int timeout) throws Exception {
		String lockKey = REDIS_LOCK_PREFIX + key;
		
		do {
			long expires = System.currentTimeMillis() + expireMsecs + 1;
            if (this.setNX(lockKey, expires)) {
                return true;
            }
            long currentExpires = this.getExpires(lockKey);
            if (currentExpires != 0 && currentExpires < System.currentTimeMillis()) {
            	long oldExpires = this.getSet(lockKey, expires);
            	//获取上一个锁到期时间，并设置现在的锁到期时间，
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldExpires != 0 && oldExpires == currentExpires) {
                	//防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受
                    //[分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    return true;
                }
            }
            
            if(timeout > 0) {
            	Thread.sleep(DEFAULT_ACQUIRY_RETRY_MILLIS);
            }
            timeout -= DEFAULT_ACQUIRY_RETRY_MILLIS;
		} while(timeout >= 0);
        return false;
	}
	
	public synchronized void keepAlived(String key) {
		final String lockKey = REDIS_LOCK_PREFIX + key;
		long expires = System.currentTimeMillis() + expireMsecs + 1;
		redisTemplate.opsForValue().set(lockKey, expires);
	}
	
	public synchronized void unlock(String key) {
		final String lockKey = REDIS_LOCK_PREFIX + key;
		redisTemplate.delete(lockKey);
    }
}