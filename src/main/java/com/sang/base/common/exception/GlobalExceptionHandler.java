package com.sang.base.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sang.base.common.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 상태 코드 400
	public ApiResponse<Void> handleIllegalArgumentException(IllegalArgumentException ex) {
		
		log.warn("비즈니스 예외 발생", ex);
		
		return ApiResponse.fail(ex.getMessage());
	}
}
