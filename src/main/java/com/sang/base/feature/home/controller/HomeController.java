package com.sang.base.feature.home.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sang.base.common.response.ApiResponse;

@RestController
public class HomeController {

	@GetMapping("/api")
	public ApiResponse<String> home() {
		return ApiResponse.success("Welcome!");
	}
}
