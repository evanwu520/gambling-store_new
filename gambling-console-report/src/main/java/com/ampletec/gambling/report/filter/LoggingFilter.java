package com.ampletec.gambling.report.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(LoggingFilter.class);


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        logger.info("Request  {} : {}", request.getMethod(), request.getRequestURI());
        filterChain.doFilter(request, response);
        logger.info("Response :{}", response.getContentType());
    }

}