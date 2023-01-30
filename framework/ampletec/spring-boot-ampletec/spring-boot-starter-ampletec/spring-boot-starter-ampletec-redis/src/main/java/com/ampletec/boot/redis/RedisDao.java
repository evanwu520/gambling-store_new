package com.ampletec.boot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class RedisDao<E> {
	
	public static final String INCR_NEXT_ID = "redis.incr_next_id";

	protected final String _id;

	public RedisDao() {
		this._id = this.getClass().getName();
	}
	
//	@Autowired
//	protected KeyGenerator keyGen;	

	@Autowired
	@Qualifier("dynamicRedisTemplate")
	@SuppressWarnings("unchecked")
	private void setRedisTemplate(RedisTemplate<?, ?> template) {
		redisTemplate = (RedisTemplate<String, E>) template;
	}
	
	protected RedisTemplate<String, E> redisTemplate;

	protected long increment() {
		return increment(genKey(RedisDao.INCR_NEXT_ID));
	}

	protected long increment(String key) {
		return redisTemplate.opsForValue().increment(key, 1);
	}

	protected String genKey(Object a1, Object... args) {
		String prefix = ":" + a1.toString() + ":";
		for (Object o : args)
			prefix += o.toString() + ".";
		return _id + prefix.substring(0, prefix.length() - 1);
	}
	
	public byte[] serialize(Object o) {
		return RedisConverter.serialize(o);
    }
 
    @SuppressWarnings("unchecked")
	public E deserialize(byte[] bytes) {
        return (E) RedisConverter.deserialize(bytes);
    }

	public E get(String key) {
		return redisTemplate.opsForValue().get(_id + key);
	}

	public void del(String key) {
		redisTemplate.opsForValue().getOperations().delete(_id + key);
	}
}

class RedisConverter
{
	private static Converter<Object, byte[]> serializer = new SerializingConverter();
    private static Converter<byte[], Object> deserializer = new DeserializingConverter();
 
    public static byte[] serialize(Object o) {
        if (o == null) return new byte[0];
        try {
            return serializer.convert(o);
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
 
    public static Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        try {
            return deserializer.convert(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
}
