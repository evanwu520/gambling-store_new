package com.ampletec.boot.redis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistributedRedisLock {
 
    private RedisTemplate<Object, Object> redisTemplate;
 
    private ThreadLocal<String> lockKey = new ThreadLocal<>();
 
    public static final String UNLOCK_LUA;
 
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }
 
    public DistributedRedisLock(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    public boolean lock(String key, long expire) {
    	return this.lock(key, expire, 0, 0);
    }
 
    /**
     * 加锁
     * @param key 锁key
     * @param expire 过期时间
     * @param retryTimes 重试次数
     * @param retryInterval 重试间隔
     * @return true 加锁成功， false 加锁失败
     */
    public boolean lock(String key, long expire, int retryTimes, long retryInterval) {
        boolean result = setRedisLock(key, expire);
        /**
         * 如果获取锁失败，进行重试
         */
        while((!result) && retryTimes-- > 0){
            try {
                log.info("lock failed, retrying..." + retryTimes);
                Thread.sleep(retryInterval);
            } catch (InterruptedException e) {
                return false;
            }
            result = setRedisLock(key, expire);
        }
        return result;
    }
 
    /**
     * 释放锁
     * @param key 锁key
     * @return true 释放成功， false 释放失败
     */
    public boolean unlock(String key) {
        /**
         * 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
         * 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
         */
        try {
            RedisCallback<Boolean> callback = (connection) -> {
                String value = lockKey.get();
                return connection.eval(UNLOCK_LUA.getBytes(), ReturnType.BOOLEAN ,1, key.getBytes(), value.getBytes());
            };
            return redisTemplate.execute(callback);
        } catch (Exception e) {
            log.error("release lock occured an exception", e);
        } finally {
            lockKey.remove();
        }
        return false;
    }
 
    /**
     * 设置redis锁
     * @param key  锁key
     * @param expire 过期时间
     * @return true 设置成功，false 设置失败
     */
    private boolean setRedisLock(String key, long expire) {
        try {
            RedisCallback<Boolean> callback = (connection) -> {
                String uuid = UUID.randomUUID().toString();
                lockKey.set(uuid);
                return connection.set(key.getBytes(), uuid.getBytes(), Expiration.milliseconds(expire), RedisStringCommands.SetOption.SET_IF_ABSENT);
            };
            return redisTemplate.execute(callback);
        }catch (Exception e){
            log.error("set redis error", e);
        }
        return false;
    }
 
    public synchronized void keepAlived(String key, long expire) {
    	redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
	}
}
