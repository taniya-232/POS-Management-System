package com.jbs.posbe.dto.request;

import com.jbs.posbe.enums.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserPatchDto {
	
	private String userName;
	
	private String userPassword;
	
	private UserRole userRole;
	
	private Boolean active;

}
