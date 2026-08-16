package com.jbs.posbe.dto.response;

import com.jbs.posbe.enums.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
	
	private String token;
	private Long userId;
	private String userName;
	private String userEmail;
	private UserRole userRole;
	
}
