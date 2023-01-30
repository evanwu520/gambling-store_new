package com.ampletec.boot.mybatis.caches.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Pool;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 缓存配置-使用Lettuce客户端，手动注入配置的方式(mybatis 缓存使用)
 * 
 * @author john
 *
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisCacheAutoConfiguration {

	@Value("${spring.redis.mybatis.database}")
	private Integer database;

	@Autowired
	private RedisProperties redisProperties;

	/**
	 * 获取mybatis缓存操作助手对象
	 * 
	 * @return
	 */
	@Bean(name = "mybatisRedisTemplate")
	public RedisTemplate<String, String> redisTemplate() {
		// 创建Redis缓存操作助手RedisTemplate对象
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(createConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());// key序列化
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());// value序列化
		template.setEnableTransactionSupport(true);// 是否启用事务
		template.afterPropertiesSet();
		return template;
	}

	/**
	 * 创建redis链接
	 * 
	 * @return
	 */
	public RedisConnectionFactory createConnectionFactory() {
		// 单机模式
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(redisProperties.getHost());
		configuration.setPort(redisProperties.getPort());
		configuration.setDatabase(database);
		configuration.setPassword(RedisPassword.of(redisProperties.getPassword()));
		LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration, getPoolConfig());
		factory.afterPropertiesSet();
		return factory;
	}

	/**
	 * 配置连接池
	 * 
	 * @return
	 */
	public LettucePoolingClientConfiguration getPoolConfig() {
		Pool poolConf = redisProperties.getLettuce().getPool();
		GenericObjectPoolConfig<?> config = new GenericObjectPoolConfig<>();
		config.setMaxTotal(poolConf.getMaxActive());
		config.setMaxWaitMillis(poolConf.getMaxWait().getSeconds());
		config.setMaxIdle(poolConf.getMaxIdle());
		config.setMinIdle(poolConf.getMinIdle());
		LettucePoolingClientConfiguration pool = LettucePoolingClientConfiguration.builder().poolConfig(config)
				.commandTimeout(redisProperties.getTimeout()).shutdownTimeout(redisProperties.getLettuce().getShutdownTimeout())
				.build();
		return pool;
	}

}
