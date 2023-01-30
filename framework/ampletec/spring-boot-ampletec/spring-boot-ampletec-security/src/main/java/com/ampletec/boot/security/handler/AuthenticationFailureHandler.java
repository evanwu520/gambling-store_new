package com.ampletec.boot.security.handler;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

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
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		JSONObject forbidden = new JSONObject();
		forbidden.put("status", HttpStatus.UNAUTHORIZED.value());
		forbidden.put("error", "Unauthorized");;
		forbidden.put("message", exception.getMessage());
		forbidden.put("path", request.getRequestURI());
		forbidden.put("timestamp", System.currentTimeMillis() / 1000);
		forbidden.put("requestid", Optional.ofNullable(request.getParameter("requestid")).orElseGet(() -> UUID.randomUUID().toString()));
		response.getWriter().append(forbidden.toString());
	}	
}
