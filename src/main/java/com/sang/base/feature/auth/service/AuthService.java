package com.sang.base.feature.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sang.base.feature.auth.dto.request.LoginRequest;
import com.sang.base.feature.auth.dto.request.SignupRequest;
import com.sang.base.feature.auth.dto.response.LoginResponse;
import com.sang.base.feature.user.entity.User;
import com.sang.base.feature.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	
	@Transactional
	public void signup(SignupRequest request) {
		
		if (userRepository.existsByEmail(request.email())) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}
		
		User user = User.register(
				request.email(),
				request.password(),
				request.nickname()
		);
		
		userRepository.save(user);
	}
	
	@Transactional(readOnly = true)
	public LoginResponse login(LoginRequest request) {
		
		User user = userRepository.findByEmail(request.email())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		
		if(!user.getPassword().equals(request.password())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		
		return new LoginResponse(
				user.getId(),
				user.getNickname()
		);
	}
}
