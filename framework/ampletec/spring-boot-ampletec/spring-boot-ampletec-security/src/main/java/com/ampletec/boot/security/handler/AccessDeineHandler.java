package com.ampletec.boot.security.handler;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.alibaba.fastjson.JSONObject;

public class AccessDeineHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		JSONObject forbidden = new JSONObject();
		forbidden.put("status", HttpStatus.UNAUTHORIZED.value());
		forbidden.put("error", "AccessDenied");;
		forbidden.put("message", "Access Denied");
		forbidden.put("path", request.getRequestURI());
		forbidden.put("timestamp", System.currentTimeMillis() / 1000);
		forbidden.put("requestid", Optional.ofNullable(request.getParameter("requestid")).orElseGet(() -> UUID.randomUUID().toString()));
		response.getWriter().append(forbidden.toString());
	}

}
