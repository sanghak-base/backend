package com.sang.base.feature.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sang.base.feature.auth.dto.request.LoginRequest;
import com.sang.base.feature.auth.dto.request.SignupRequest;
import com.sang.base.feature.auth.dto.response.LoginResponse;
import com.sang.base.feature.user.entity.User;
import com.sang.base.feature.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	
	@Transactional
	public void signup(SignupRequest request) {
		
		if (userRepository.existsByEmail(request.email())) {
			log.warn("회원가입 실패 - 중복 이메일 email={}", request.email());
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}
		
		User user = User.register(
				request.email(),
				request.password(),
				request.nickname()
		);
		
		userRepository.save(user);
		
		log.info("회원가입 완료 userId={}, email={}", user.getId(), user.getEmail());
	}
	
	@Transactional(readOnly = true)
	public LoginResponse login(LoginRequest request) {
		
		User user = userRepository.findByEmail(request.email())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		
		if(!user.getPassword().equals(request.password())) {
			log.warn("로그인 실패 - 비밀번호 불일치 email={}", request.email());
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		
		log.info("로그인 성공 userId={}, email={}", user.getId(), user.getEmail());
		
		return new LoginResponse(
				user.getId(),
				user.getNickname()
		);
	}
}
