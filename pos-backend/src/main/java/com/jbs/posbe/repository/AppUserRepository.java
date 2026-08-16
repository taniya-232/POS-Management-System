package com.jbs.posbe.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jbs.posbe.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	
	/*
	 * Used during login to find the user by email
	 */
	Optional<AppUser> findByUserEmail(String userEmail);
	
	/*
	 * Used to check if a user with the given email already exists
	 */
	boolean existsByUserEmail(String userEmail);
	
	/*
	 * Find active user by email
	 * Used during authentication
	 */
	Optional<AppUser> findByUserEmailAndActiveTrue(String userEmail);
	
	/*
	 * Paginated active users
	 */
	Page<AppUser> findByActiveTrue(Pageable pageable);
}
