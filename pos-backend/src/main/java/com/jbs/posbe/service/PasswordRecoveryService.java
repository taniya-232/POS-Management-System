package com.jbs.posbe.service;

import com.jbs.posbe.dto.request.ForgotPasswordRequestDto;
import com.jbs.posbe.dto.request.ResetPasswordRequestDto;
import com.jbs.posbe.dto.request.VerifyOtpRequestDto;

public interface PasswordRecoveryService {
	
	/*
	 * Generates and email OTP.
	 */
	void sendOtp(ForgotPasswordRequestDto forgotPasswordRequestDto);
	
	/*
	 * Verifies the OTP.
	 */
	void verifyOtp(VerifyOtpRequestDto verifyOtpRequestDto);
	
	/*
	 * Resets the password.
	 */
	void resetPassword(ResetPasswordRequestDto resetPasswordRequestDto);
}
