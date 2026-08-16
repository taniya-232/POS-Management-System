package com.jbs.posbe.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jbs.posbe.dto.request.AppUserPatchDto;
import com.jbs.posbe.dto.request.AppUserRequestDto;
import com.jbs.posbe.dto.response.AppUserResponseDto;
import com.jbs.posbe.entity.AppUser;
import com.jbs.posbe.repository.AppUserRepository;
import com.jbs.posbe.service.AppUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

	private final AppUserRepository appUserRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	// SAVE USER
	@Override
	public AppUserResponseDto saveUser(AppUserRequestDto appUserRequestDto) {
		
		if(appUserRepository.existsByUserEmail(appUserRequestDto.getUserEmail())) {
			throw new RuntimeException("Email already exists");
		}
		
		AppUser appUser = new AppUser();
		
		appUser.setUserName(appUserRequestDto.getUserName());
		appUser.setUserEmail(appUserRequestDto.getUserEmail());
		appUser.setUserPassword(passwordEncoder.encode(appUserRequestDto.getUserPassword()));
		appUser.setUserRole(appUserRequestDto.getUserRole());
		
		if(appUserRequestDto.getActive() != null) {
			appUser.setActive(appUserRequestDto.getActive());
		}
		
		AppUser savedUser = appUserRepository.save(appUser);
		
		return convertToDto(savedUser);
	}
	
	// LIST USERS
	@Override
	public Page<AppUserResponseDto> getAllUsers(Pageable pageable) {
		Page<AppUser> appUsers = appUserRepository.findAll(pageable);
		return appUsers.map(this::convertToDto);
	}
	
	// GET USER BY ID
	@Override
	public AppUserResponseDto getUserById(Long userId) {
		AppUser appUser = getUserEntity(userId);
		return convertToDto(appUser);
	}
	
	// UPDATE USER
	@Override
	public AppUserResponseDto updateUser(Long userId, AppUserPatchDto appUserPatchDto) {
		
		AppUser appUser = getUserEntity(userId);
		
		if(appUserPatchDto.getUserName() != null) {
			appUser.setUserName(appUserPatchDto.getUserName());
		}
		
		if(appUserPatchDto.getUserPassword() != null) {
			appUser.setUserPassword(passwordEncoder
					.encode(appUserPatchDto.getUserPassword()));
		}
		
		if(appUserPatchDto.getUserRole() != null) {
			appUser.setUserRole(appUserPatchDto.getUserRole());
		}
		
		if(appUserPatchDto.getActive() != null) {
			appUser.setActive(appUserPatchDto.getActive());
		}
		
		AppUser updatedUser = appUserRepository.saveAndFlush(appUser);
		
		return convertToDto(updatedUser);
	}
	
	// DELETE USER
	@Override
	public void deleteUser(Long userId) {
		AppUser appUser = getUserEntity(userId);
		appUserRepository.delete(appUser);
	}
	
	// Helper methods
	private AppUser getUserEntity(Long userId) {
		return appUserRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
	}
	
	private AppUserResponseDto convertToDto(AppUser appUser) {
		AppUserResponseDto appUserResponseDto = new AppUserResponseDto();
		appUserResponseDto.setUserId(appUser.getUserId());
		appUserResponseDto.setUserName(appUser.getUserName());
		appUserResponseDto.setUserEmail(appUser.getUserEmail());
		appUserResponseDto.setUserRole(appUser.getUserRole().name());
		appUserResponseDto.setActive(appUser.isActive());
		/*
		 * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 * dto.setCreatedAt(appUser.getCreatedAt().format(formatter));
		 * dto.setUpdatedAt(appUser.getUpdatedAt().format(formatter));
		 */
		appUserResponseDto.setCreatedAt(appUser.getCreatedAt().toString());
		appUserResponseDto.setUpdatedAt(appUser.getUpdatedAt().toString());
		return appUserResponseDto;
	}
}
