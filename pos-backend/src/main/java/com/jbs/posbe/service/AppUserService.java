package com.jbs.posbe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jbs.posbe.dto.request.AppUserPatchDto;
import com.jbs.posbe.dto.request.AppUserRequestDto;
import com.jbs.posbe.dto.response.AppUserResponseDto;

public interface AppUserService {
	
	AppUserResponseDto saveUser(AppUserRequestDto appUserRequestDto);
	Page<AppUserResponseDto> getAllUsers(Pageable pageable);
	AppUserResponseDto getUserById(Long userId);
	AppUserResponseDto updateUser(Long userId, AppUserPatchDto appUserPatchDto);
	void deleteUser(Long userId);
}
