package com.jbs.posbe.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jbs.posbe.dto.request.ForgotPasswordRequestDto;
import com.jbs.posbe.dto.request.ResetPasswordRequestDto;
import com.jbs.posbe.dto.request.VerifyOtpRequestDto;
import com.jbs.posbe.entity.AppUser;
import com.jbs.posbe.entity.UserOtp;
import com.jbs.posbe.repository.AppUserRepository;
import com.jbs.posbe.repository.UserOtpRepository;
import com.jbs.posbe.service.EmailService;
import com.jbs.posbe.service.PasswordRecoveryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {

	private final AppUserRepository appUserRepository;
	private final UserOtpRepository userOtpRepository;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void sendOtp(ForgotPasswordRequestDto forgotPasswordRequestDto) {
		AppUser appUser = appUserRepository
				.findByUserEmail(forgotPasswordRequestDto.getUserEmail())
				.orElseThrow(() -> 
				new RuntimeException("User not found with email: " + 
				forgotPasswordRequestDto.getUserEmail()));
		
		String otpCode = generateOtp();
		
		UserOtp userOTP = new UserOtp();
		userOTP.setUser(appUser);
		userOTP.setOtpCode(passwordEncoder.encode(otpCode));
		userOTP.setExpiryTime(LocalDateTime.now().plusMinutes(5)); // OTP valid for 5 minutes
		userOTP.setVerified(false);
		userOTP.setUsed(false);
		
		userOtpRepository.save(userOTP);
		
		String subject = "Your OTP for Password Recovery";
		
		String body = "Your OTP is: " + otpCode + ". It is valid for 5 minutes." 
				+ "\n\nRegards,\nPOS Management System";
		emailService.sendEmail(appUser.getUserEmail(), subject, body);
	}

	@Override
	public void verifyOtp(VerifyOtpRequestDto verifyOtpRequestDto) {
		AppUser appUser = appUserRepository
				.findByUserEmail(verifyOtpRequestDto.getUserEmail())
				.orElseThrow(() -> 
				new RuntimeException("User not found with email: " + 
				verifyOtpRequestDto.getUserEmail()));
		
		UserOtp userOTP = userOtpRepository
				.findTopByUserOrderByCreatedAtDesc(appUser)
				.orElseThrow(() -> 
				new RuntimeException("No OTP found for user with email: " + 
				verifyOtpRequestDto.getUserEmail()));
		
		if (userOTP.isUsed()) {
			throw new RuntimeException("OTP has already been used.");
		}
		
		if (userOTP.getExpiryTime().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("OTP has expired.");
		}
		
		if (!passwordEncoder.matches(verifyOtpRequestDto.getOtp(), userOTP.getOtpCode())) {
			throw new RuntimeException("Invalid OTP.");
		}
		
		userOTP.setVerified(true);
		
		userOtpRepository.save(userOTP);
	}

	@Override
	public void resetPassword(ResetPasswordRequestDto resetPasswordRequestDto) {
		AppUser appUser = appUserRepository
				.findByUserEmail(resetPasswordRequestDto.getUserEmail())
				.orElseThrow(() -> 
				new RuntimeException("User not found with email: " + 
				resetPasswordRequestDto.getUserEmail()));
		
		UserOtp userOTP = userOtpRepository
				.findTopByUserOrderByCreatedAtDesc(appUser)
				.orElseThrow(() -> 
				new RuntimeException("No OTP found for user with email: " + 
				resetPasswordRequestDto.getUserEmail()));
		
		if (!userOTP.isVerified()) {
			throw new RuntimeException("OTP has not been verified.");
		}
		
		if (userOTP.isUsed()) {
			throw new RuntimeException("OTP has already been used.");
		}
		
		if (userOTP.getExpiryTime().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("OTP has expired.");
		}
		
		appUser.setUserPassword(passwordEncoder.encode(resetPasswordRequestDto.getNewPassword()));
		
		appUserRepository.save(appUser);
		
		userOTP.setUsed(true);
		
		userOtpRepository.save(userOTP);
	}
	
	/*
	 * Generates a random OTP.
	 */
	private String generateOtp() {
		
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000); // Generates a 6-digit OTP
		return String.valueOf(otp);
	}

}
