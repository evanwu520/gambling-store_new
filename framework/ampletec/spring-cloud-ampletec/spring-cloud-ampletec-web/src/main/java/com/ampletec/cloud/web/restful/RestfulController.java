package com.ampletec.cloud.web.restful;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public abstract class RestfulController {

	protected JSONObject response() {
		return RestResponse.OK;
	}

	protected JSONObject response(Serializable data) {
		return RestResponse.ok(data);
	}

	protected JSONObject response(JSONObject data) {
		return RestResponse.ok(data);
	}
	
	protected JSONObject response(int error, String message) {
		return RestResponse.error(error, message);
	} 
}
