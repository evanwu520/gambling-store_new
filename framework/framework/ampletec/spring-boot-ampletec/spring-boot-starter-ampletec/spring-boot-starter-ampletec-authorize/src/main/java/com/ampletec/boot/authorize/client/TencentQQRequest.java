package com.ampletec.boot.authorize.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.ampletec.boot.authorize.client.autoconfigure.TencentQQProperties;
import com.ampletec.boot.authorize.client.exception.TencentException;
import com.ampletec.boot.authorize.client.model.AccessToken;
import com.ampletec.boot.authorize.client.redis.TokenRedisDao;
import com.ampletec.commons.lang.DateTime;
import com.ampletec.commons.lang.SplitCharacter;

@Component
public class TencentQQRequest {

	private String ACCESS_TOKEN = "access_token";

	@Autowired
	private RestTemplate restTemplate;
	@Autowired(required=false)
	private TencentQQProperties config;
	@Autowired(required=false)
	private TokenRedisDao tokenRedisDao;
	
	private JSONObject requestObject(String url, Map<String, String> params) throws TencentException {
		JSONObject json = this.restTemplate.getForObject(config.getTokenRequest(), JSONObject.class, params);
		if (json.containsKey("errcode") && json.getIntValue("errcode") > 0) {
			throw new TencentException(json.getIntValue("errcode"), json.getString("errmsg"));
		}
		return json;
	}

	/**
	 * 获取应用token
	 *
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public AccessToken getAccessToken(String appid, String appsecret, String code, String redirect) throws Exception {
		AccessToken token = null;
		String access_token = tokenRedisDao.getString(ACCESS_TOKEN + SplitCharacter.COLON + appid);
		if (StringUtils.isEmpty(access_token)) {
			Map<String, String> params = new HashMap<>();
			params.put("grant_type", "authorization_code");
			params.put("client_id", appid);
			params.put("client_secret", appsecret);
			params.put("code", code);
			params.put("redirect_uri", URLEncoder.encode(redirect, "utf-8"));
			params.put("fmt", "json");

			JSONObject json = this.requestObject(config.getTokenRequest(), params);
			
			access_token = (String) json.getString(ACCESS_TOKEN);
			int expires_in = json.getIntValue("expires_in");
			if (access_token == null) return null;
			tokenRedisDao.set(ACCESS_TOKEN + SplitCharacter.COLON + appid, expires_in - 500, access_token);

			String openId = requestOpenId(access_token);
			token = new AccessToken(access_token, expires_in, DateTime.now(), openId, json.getString("refresh_token"));
		}
		return token;
	}

	/**
	 * 获取授权接口
	 *
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getAuthorizeUrl(String appid, String redirect) throws UnsupportedEncodingException {
		return config.getAuthorizeRequest() + "?client_id=" + appid + "&response_type=code&redirect_uri="
		+ URLEncoder.encode(redirect, "utf-8") + "&scope=get_user_info,list_album,upload_pic,do_like";
	}

	public String requestOpenId(String access_token) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("access_token", access_token);
		params.put("fmt", "json");
		JSONObject json = this.requestObject(config.getOpenidRequest(), params);
		return json.getString("openid");
	}

	/**
	 * 获取用户token
	 *
	 * @param request
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	public JSONObject getUserInfo(String appid, AccessToken token) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("access_token", token.getAccessToken());
		params.put("oauth_consumer_key", appid);
		params.put("openid", token.getOpenid());
		return this.requestObject(config.getInfoRequest(), params);
	}
}