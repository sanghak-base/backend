package com.sang.base.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T> (
		boolean success,
		T data,
		String message
) {
	
	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, data, null);
	}
	
	public static ApiResponse<Void> emptySuccess() {
		return new ApiResponse<>(true, null, null);
	}
	
	public static <T> ApiResponse<T> fail(String message) {
		return new ApiResponse<>(false, null, message);
	}
}