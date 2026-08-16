package com.jbs.posbe.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserResponseDto {
	
	private Long userId;
	
	private String userName;
	
	private String userEmail;
	
	private String userRole;
	
	private Boolean active;
	
	private String createdAt;
	
	private String updatedAt;

}
