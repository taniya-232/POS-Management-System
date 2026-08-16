package com.jbs.posbe.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_otps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOtp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "otp_id")
	private Long otpId;
	
	/*
	 * Many OTPs -> One User
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private AppUser user;
	
	/*
	 * BCrypt Encrypted OTP:
	 */
	@Column(name = "otp_code", nullable = false)
	private String otpCode;
	
	/*
	 * OTP Expiration Time:
	 */
	@Column(name = "expiry_time", nullable = false)
	private LocalDateTime expiryTime;
	
	/*
	 * Whether OTP already used or not:
	 */
	private boolean used = false;
	
	/*
	 * Whether OTP is verified or not:
	 */
	@Column(name = "is_verified", nullable = false)
	private boolean verified = false;
	
	/*
	 * OTP Creation Timestamp:
	 */
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
}
