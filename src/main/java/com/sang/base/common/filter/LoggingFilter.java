package com.sang.base.common.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if ("OPTIONS".equals(request.getMethod())) {
		    filterChain.doFilter(request, response);
		    return;
		}
		
		String traceId = UUID.randomUUID().toString();
		
		MDC.put("traceId", traceId);
		
		long startTime = System.currentTimeMillis();
		
		log.info("[REQUEST] {} {}", request.getMethod(), request.getRequestURI());
		
		try {
            filterChain.doFilter(request, response);
        } finally {
            long endTime = System.currentTimeMillis();

            log.info(
                    "[RESPONSE] {} {} status={} elapsed={}ms",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    endTime - startTime
            );
            
            MDC.clear();
        }
    }
}