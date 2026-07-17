package com.sang.base.feature.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sang.base.common.response.ApiResponse;
import com.sang.base.feature.auth.dto.request.LoginRequest;
import com.sang.base.feature.auth.dto.request.SignupRequest;
import com.sang.base.feature.auth.dto.response.LoginResponse;
import com.sang.base.feature.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/signup")
	public ApiResponse<Void> signup(
			@Valid @RequestBody SignupRequest request
	) {
		
		log.info("회원가입 요청 email={}", request.email());
		
		authService.signup(request);
		
		return ApiResponse.emptySuccess();
	}
	
	@PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

		log.info("로그인 요청 email={}", request.email());
		
        return ApiResponse.success(authService.login(request));
    }
}
