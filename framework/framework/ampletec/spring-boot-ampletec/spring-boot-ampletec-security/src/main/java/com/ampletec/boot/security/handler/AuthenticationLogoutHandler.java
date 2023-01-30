package com.ampletec.boot.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class AuthenticationLogoutHandler implements LogoutHandler {

	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		SecurityContextHolder.clearContext();
	}

}
