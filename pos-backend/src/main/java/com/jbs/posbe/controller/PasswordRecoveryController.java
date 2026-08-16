package com.jbs.posbe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.jbs.posbe.dto.ManagedApiResponse;
import com.jbs.posbe.dto.request.ForgotPasswordRequestDto;
import com.jbs.posbe.dto.request.ResetPasswordRequestDto;
import com.jbs.posbe.dto.request.VerifyOtpRequestDto;
import com.jbs.posbe.service.PasswordRecoveryService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class PasswordRecoveryController {
	
	private final PasswordRecoveryService passwordRecoveryService;
	
	// ---------------------------------------------------------------------
	@Operation(
			tags = "Authentication", 
			summary = "Forgot Password", 
			description = "Generates and sends a 6-digit OTP to the registered email address of the user for password recovery.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "OTP send successfully"),
			@ApiResponse(responseCode = "404", description = "Registered email not found"),
			@ApiResponse(responseCode = "500", description = "Error sending OTP email")
	})
	@PostMapping("/forgot-password")
	public ResponseEntity<ManagedApiResponse<Void>> forgotPassword(
			@Valid @RequestBody ForgotPasswordRequestDto dto) {
		
		passwordRecoveryService.sendOtp(dto);
		
		ManagedApiResponse<Void> response = 
				new ManagedApiResponse<>(HttpStatus.OK.value(), 
						"OTP send successfully to registered email", 
						null);
		
		return ResponseEntity.ok(response);
	}
	
	// ---------------------------------------------------------------------
	@Operation(
			tags = "Authentication", 
			summary = "Verify OTP", 
			description = "Verify OTP sent to the registered email address of the user for password recovery.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "OTP verified successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid or expired OTP"),
			@ApiResponse(responseCode = "500", description = "Registered email not found")
	})
	@PostMapping("/verify-otp")
	public ResponseEntity<ManagedApiResponse<Void>> verifyOTP(
			@Valid @RequestBody VerifyOtpRequestDto dto) {
		
		passwordRecoveryService.verifyOtp(dto);
		
		ManagedApiResponse<Void> response = 
				new ManagedApiResponse<>(HttpStatus.OK.value(), 
						"OTP verified successfully.", 
						null);
		
		return ResponseEntity.ok(response);
	}
	
	// ---------------------------------------------------------------------
	@Operation(
			tags = "Authentication", 
			summary = "Reset Password", 
			description = "Updates the user's password after verifying the OTP sent to the registered email address.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Password updated successfully"),
			@ApiResponse(responseCode = "400", description = "OTP verification required before password reset"),
			@ApiResponse(responseCode = "404", description = "Registered email not found")
	})
	@PostMapping("/reset-password")
	public ResponseEntity<ManagedApiResponse<Void>> resetPassword(
			@Valid @RequestBody ResetPasswordRequestDto dto) {
		
		passwordRecoveryService.resetPassword(dto);
		
		ManagedApiResponse<Void> response = 
				new ManagedApiResponse<>(HttpStatus.OK.value(), 
						"Password updated successfully.", 
						null);
		
		return ResponseEntity.ok(response);
	}
}
