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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpStatus.FORBIDDEN.value());
		JSONObject forbidden = new JSONObject();
		forbidden.put("status", HttpStatus.FORBIDDEN.value());
		forbidden.put("error", "Forbidden");;
		forbidden.put("message", "Ampletec JWT Forbidden");
		forbidden.put("path", request.getRequestURI());
		forbidden.put("timestamp", System.currentTimeMillis() / 1000);
		forbidden.put("requestid", Optional.ofNullable(request.getParameter("requestid")).orElseGet(() -> UUID.randomUUID().toString()));
		response.getWriter().append(forbidden.toString());
	}

}
