package com.jbs.posbe.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequestDto {
	
	@NotBlank(message = "User email is required")
	@Email(message = "Invalid email format")
	private String userEmail;

}
