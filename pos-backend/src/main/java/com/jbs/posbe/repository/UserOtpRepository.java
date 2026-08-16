package com.jbs.posbe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jbs.posbe.entity.AppUser;
import com.jbs.posbe.entity.UserOtp;

public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {
	
	/*
	 * Returns the latest OTP generated for a user.
	 */
	Optional<UserOtp> findTopByUserOrderByCreatedAtDesc(AppUser appUser);
}
