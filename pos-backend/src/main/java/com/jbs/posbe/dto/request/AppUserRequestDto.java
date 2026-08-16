package com.jbs.posbe.dto.request;

import com.jbs.posbe.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserRequestDto {
	
	@NotBlank(message = "User name is required")
	private String userName;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String userEmail;
	
	@NotBlank(message = "Password is required")
	private String userPassword;
	
	@NotNull(message = "Role is required")
	private UserRole userRole;
	
	private Boolean active = true;

}
