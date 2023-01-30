package com.ampletec.boot.redis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisTemplateManager {

	private static final Map<String, RedisTemplate<?, ?>> templates_key = new HashMap<>();
	private static final Map<Class<?>, RedisTemplate<?, ?>> templates_class = new HashMap<>();
	
	public static void put(RedisTemplate<?, ?> template) {
		templates_class.put(template.getClass(), template);
	}
	
	public static RedisTemplate<?, ?> get(Class<?> T) {
		return templates_class.get(T);
	}
	
	public static void put(String name, RedisTemplate<?, ?> template) {
		templates_key.put(name, template);
	}
	
	public static RedisTemplate<?, ?> get(String name) {
		return templates_key.get(name);
	}
}
