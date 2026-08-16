package com.jbs.posbe.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.jbs.posbe.dto.request.LoginRequestDto;
import com.jbs.posbe.dto.response.LoginResponseDto;
import com.jbs.posbe.entity.AppUser;
import com.jbs.posbe.repository.AppUserRepository;
import com.jbs.posbe.security.JWTService;
import com.jbs.posbe.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final AppUserRepository appUserRepository;
	private final JWTService jwtService;
	 
	@Override
	public LoginResponseDto login(LoginRequestDto dto) {
		
		// authenticate email and password
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(dto.getUserEmail(), dto.getUserPassword()));
		
		// Load User
		AppUser user = appUserRepository.findByUserEmail(dto.getUserEmail())
				.orElseThrow(() -> new RuntimeException("Invalid Credential"));
		
		// Active Check
		if(!user.isActive()) {
			throw new RuntimeException("User Account is Inactive");
		}
		
		// Generate JWT
		String token = jwtService.generateToken(user);
		
		// Response DTO
		LoginResponseDto response = new LoginResponseDto();
		response.setToken(token);
		response.setUserId(user.getUserId());
		response.setUserName(user.getUserName());
		response.setUserEmail(user.getUserEmail());
		response.setUserRole(user.getUserRole());
		
		return response;
	}

}
