package com.ampletec.cloud.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import com.alibaba.fastjson.JSONObject;

public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler{

	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpStatus.FORBIDDEN.value());
		JSONObject forbidden = new JSONObject();
		forbidden.put("status", HttpStatus.UNAUTHORIZED.value());
		forbidden.put("error", "Unauthorized");;
		forbidden.put("message", exception.getMessage());
		forbidden.put("path", request.getRequestURI());
		forbidden.put("timestamp", System.currentTimeMillis() / 1000);
		response.getWriter().append(forbidden.toString());
	}	
}
