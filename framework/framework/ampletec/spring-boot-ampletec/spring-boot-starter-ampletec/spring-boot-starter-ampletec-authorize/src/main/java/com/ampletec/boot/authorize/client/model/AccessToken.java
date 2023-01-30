package com.ampletec.boot.authorize.client.model;

import com.ampletec.commons.lang.DateTime;

public class AccessToken {

	private String accessToken;
	private String openid;
	private int expires;
	private int refreshTime;
	private String refreshToken;

	public AccessToken() {
		super();
	}

	public AccessToken(String accessToken, int expires, int refreshTime, String openid, String refreshToken) {
		super();
		this.accessToken = accessToken;
		this.expires = expires;
		this.refreshTime = refreshTime;
		this.openid = openid;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpires() {
		return expires;
	}

	public void setExpires(int expires) {
		this.expires = expires;
	}

	public int getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(int refreshTime) {
		this.refreshTime = refreshTime;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public boolean isExpires() {
		boolean ret = true;
		int time = DateTime.now() - refreshTime;
		if (expires - time > 1500)
			ret = false;
		return ret;
	}

	public String toString() {
		return "{accessToken:" + accessToken + ",expires:" + expires + ",refreshTime:" + refreshTime + ",openid:"
				+ openid + ",refreshToken:" + refreshToken + "}";
	}
}
