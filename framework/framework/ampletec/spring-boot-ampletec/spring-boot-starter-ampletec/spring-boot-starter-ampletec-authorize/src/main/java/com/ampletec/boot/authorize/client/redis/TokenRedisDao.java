package com.ampletec.boot.authorize.client.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.ampletec.boot.redis.RedisDao;


@Component
public class TokenRedisDao extends RedisDao<String> {

	private static final int EXPIRE_DURATION_SECONDS = 24 * 60 * 60;
	private static final String TOKEN_STATUS = "Token.status:";
	private static final String TOKEN_REFRESH = "Token.refresh";

	@Autowired
	private StringRedisTemplate redisTemplate;

	public String getString(String key) throws Exception {
		return redisTemplate.opsForValue().get(genKey(TokenRedisDao.TOKEN_STATUS, key));
	}

	public void set(String key, int seconds, String value) throws Exception {
		redisTemplate.opsForValue().set(genKey(TokenRedisDao.TOKEN_STATUS, key), value, seconds, TimeUnit.SECONDS);
	}

	public boolean refresh(long userid) throws Exception {
		boolean isRef = redisTemplate.execute(new RedisCallback<Boolean>() {
		    public Boolean doInRedis(RedisConnection conn) throws DataAccessException {
		        
		    	boolean isRef = false;
		        byte[] key = genKey(TOKEN_REFRESH, userid).getBytes();;
		        byte[] ret = conn.get(key);
		        if (ret == null || ret.length == 0) {
					isRef = true;
					conn.set(key, String.valueOf(System.currentTimeMillis()).getBytes());
					conn.expire(key, EXPIRE_DURATION_SECONDS);
				}
		        
		        return isRef;
		    }
		});
		return isRef;
	}

}
