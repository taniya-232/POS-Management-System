package com.jbs.posbe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbs.posbe.dto.request.LoginRequestDto;
import com.jbs.posbe.service.AuthService;
import com.jbs.posbe.dto.response.LoginResponseDto;
import com.jbs.posbe.dto.ManagedApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	// ---------------------------------------------------------------------
	@Operation(
			tags = "Authentication", 
			summary = "Login to application", 
			description = "Authenticates a user and returns a JWT token for subsequent requests.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Login successfully"),
			@ApiResponse(responseCode = "401", description = "Invalid credential"),
			@ApiResponse(responseCode = "400", description = "Invalid request body")
	})
	@PostMapping("/login")
	public ResponseEntity<ManagedApiResponse<LoginResponseDto>> login(
			@Valid @RequestBody LoginRequestDto dto) {
		
		LoginResponseDto loginResponse = authService.login(dto);
		
		ManagedApiResponse<LoginResponseDto> response = 
				new ManagedApiResponse<>(HttpStatus.OK.value(), 
						"Login successfully", 
						loginResponse);
		
		return ResponseEntity.ok(response);
	}
	
}
