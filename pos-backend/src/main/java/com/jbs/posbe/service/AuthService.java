package com.jbs.posbe.service;

import com.jbs.posbe.dto.request.LoginRequestDto;
import com.jbs.posbe.dto.response.LoginResponseDto;

public interface AuthService {
	
	LoginResponseDto login(LoginRequestDto dto);
}
