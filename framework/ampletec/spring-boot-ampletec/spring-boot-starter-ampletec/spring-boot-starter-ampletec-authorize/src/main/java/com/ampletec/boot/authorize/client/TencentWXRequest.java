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
import com.ampletec.boot.authorize.client.autoconfigure.TencentWXProperties;
import com.ampletec.boot.authorize.client.exception.TencentException;
import com.ampletec.boot.authorize.client.model.AccessToken;
import com.ampletec.boot.authorize.client.redis.TokenRedisDao;
import com.ampletec.commons.lang.DateTime;
import com.ampletec.commons.lang.FixedLinkedHashMap;
import com.ampletec.commons.lang.SplitCharacter;
import com.ampletec.commons.lang.URLVariables;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class TencentWXRequest {

	private String ACCESS_TOKEN = "access_token";
	private static Map<String, AccessToken> m_sTokenMap = new FixedLinkedHashMap<String, AccessToken>(2000);
	@Autowired(required=false)
	private TencentWXProperties config;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired(required=false)
	private TokenRedisDao tokenRedisDao;	
	
	private JSONObject requestObject(String url, Map<String, String> params) throws TencentException {
		url += "?" + URLVariables.encode(params, false, false, false);
		String response = this.restTemplate.getForObject(url, String.class);
		JSONObject json = JSONObject.parseObject(response);
		if (json.containsKey("errcode") && json.getIntValue("errcode") > 0) {
			log.error("TencentWXRequest url:{} Respone:{}", url, response);
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
	public String getAccessToken(String appid, String appsecret) throws Exception {
		String access_token = tokenRedisDao.getString(ACCESS_TOKEN + SplitCharacter.COLON + appid);
		if (StringUtils.isEmpty(access_token)) {
			Map<String, String> params = new HashMap<>();
			params.put("grant_type", "client_credential");
			params.put("appid", appid);
			params.put("secret", appsecret);
			
			JSONObject json = this.requestObject(config.getTokenRequest(), params);
			access_token = (String) json.getString(ACCESS_TOKEN);
			int expires_in = json.getIntValue("expires_in");
			if (access_token == null) return null;
			tokenRedisDao.set(ACCESS_TOKEN + SplitCharacter.COLON + appid, expires_in - 500, access_token);
		}
		return access_token;
	}

	/**
	 * 获取授权接口
	 *
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getAuthorizeUrl(String appid, String redirect) throws UnsupportedEncodingException {
		return config.getAuthorizeRequest() + "?appid=" + appid + "&redirect_uri="
				+ URLEncoder.encode(redirect, "utf-8") + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
	}

	/**
	 * 获取用户token
	 * 
	 * @param request
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	public AccessToken getUserAccessToken(String appid, String appsecret, String usercode, boolean isCache) throws Exception {
		AccessToken token = null;
		if (isCache)
			token = m_sTokenMap.get(usercode);
		if (null == token || token.isExpires()) {
			Map<String, String> params = new HashMap<>();
			params.put("appid", appid);
			params.put("secret", appsecret);
			params.put("code", usercode);
			params.put("grant_type", "authorization_code");
			
			JSONObject json = this.requestObject(config.getUserRequest(), params);
			String access_token = json.getString(ACCESS_TOKEN);
			int expires_in = json.getIntValue("expires_in");

			token = new AccessToken(access_token, expires_in, DateTime.now(), json.getString("openid"), json.getString("refresh_token"));
			m_sTokenMap.put(usercode, token);
		}
		return token;
	}

	/**
	 * @param config
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	public JSONObject getTicket(String appid, String appsecret) throws Exception {
		String access_token = getAccessToken(appid, appsecret);
		Map<String, String> params = new HashMap<>();
		params.put("access_token", access_token);
		params.put("type", "jsapi");
		return this.requestObject(config.getTicketRequest(), params);
	}

	/**
	 * 获取用户token
	 *
	 * @param request
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	public JSONObject getUserInfo(AccessToken token) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("access_token", token.getAccessToken());
		params.put("openid", token.getOpenid());
		params.put("lang", "zh_CN");
		return this.requestObject(config.getInfoRequest(), params);
	}
}