package com.ampletec.boot.mybatis.caches.redis;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import com.ampletec.boot.spring.context.SpringContextProvider;

/**
 * Cache adapter for Redis
 * 
 * @author john
 *
 */
public final class RedisCache implements Cache {

	private final ReadWriteLock readWriteLock = new DummyReadWriteLock();

	private String id;

	private RedisTemplate<String, String> template;

	public RedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		this.id = id;
	}

	@SuppressWarnings("unchecked")
	private Object execute(RedisCallback<?> callback) {
		if (template == null)
			template = (RedisTemplate<String, String>) SpringContextProvider.getBean("mybatisRedisTemplate");
		return template.execute(callback);
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public int getSize() {
		return (Integer) execute(new RedisCallback<Integer>() {
			@Override
			public Integer doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> result = connection.hGetAll(id.toString().getBytes());
				return result.size();
			}
		});
	}

	@Override
	public void putObject(final Object key, final Object value) {
		execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				GenericJackson2JsonRedisSerializer Serializer = new GenericJackson2JsonRedisSerializer();
				return connection.hSet(id.toString().getBytes(), key.toString().getBytes(), Serializer.serialize(value));
			}
		});
	}

	@Override
	public Object getObject(final Object key) {
		return execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] ret = connection.hGet(id.toString().getBytes(), key.toString().getBytes());
				GenericJackson2JsonRedisSerializer Serializer = new GenericJackson2JsonRedisSerializer();
				return Serializer.deserialize(ret);
			}
		});
	}

	@Override
	public Object removeObject(final Object key) {
		return execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] ret = connection.hGet(id.toString().getBytes(), key.toString().getBytes());
				connection.hDel(id.toString().getBytes(), key.toString().getBytes());
				GenericJackson2JsonRedisSerializer Serializer = new GenericJackson2JsonRedisSerializer();
				return Serializer.deserialize(ret);
			}
		});
	}

	@Override
	public void clear() {
		execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.del(id.toString().getBytes());
				return null;
			}
		});
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}

	@Override
	public String toString() {
		return "Redis {" + id + "}";
	}
}
