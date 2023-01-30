package com.ampletec.boot.web.restful;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public final class RestResponse {
	
	public final static JSONObject OK = JSONObject.parseObject("{'result':0,'message':'ok'}");

	public final static JSONObject ILLEGAL = JSONObject.parseObject("{'result':400,'message':'bad requset'}");	

	public final static JSONObject UNAUTHORIZED = JSONObject.parseObject("{'result':401,'message':'unauthorized'}");

	public final static JSONObject FORBIDDEN = JSONObject.parseObject("{'result':403,'message':'forbidden'}");

	public final static JSONObject ERROR = JSONObject.parseObject("{'result':500,'message':'internal server error'}");
	
	public final static JSONObject TIMEOUT = JSONObject.parseObject("{'result':502,'message':'timeout'}");
	
	public final static JSONObject UNAVAILABLE = JSONObject.parseObject("{'result':503,'message':'No server is available to handle this request.'}");
	
	@Deprecated
	public final static JSONObject response(Serializable data) {
		return response(JSON.toJSONString(data));
	}

	@Deprecated
	public final static JSONObject response(JSONObject data) {
		JSONObject resp = RestResponse.OK;
		resp.put("responseid", UUID.randomUUID().toString());
		resp.put("data", data);
		return resp;
	}
	
	public final static JSONObject error(int error, String message) {
		JSONObject resp = RestResponse.ERROR;
		resp.put("responseid", UUID.randomUUID().toString());
		resp.put("result", error);
		resp.put("message", message);
		return resp;
	}
	
	public final static JSONObject ok(JSONObject data) {
		return response(data);
	}
	
	public final static JSONObject ok(String data) {
		JSONObject resp = RestResponse.OK;
		resp.put("responseid", UUID.randomUUID().toString());
		resp.put("data", data);
		return resp;
	}
	
	public final static JSONObject ok(Serializable data) {
		return response(JSON.toJSONString(data));
	}
	
	public final static JSONObject ok(long total, List<?> data) {
		return RestResponse.ok(total, JSON.toJSONString(data));
	}
	
	public static JSONObject ok(long total, String data) {
		// TODO Auto-generated method stub
		JSONObject resp = RestResponse.OK;
		resp.put("responseid", UUID.randomUUID().toString());
		resp.put("total", total);		
		resp.put("data", data);
		return resp;
	}

	public final static JSONObject ok(long total, JSONArray data) {
		return RestResponse.ok(total, JSON.toJSONString(data));
	}
}
