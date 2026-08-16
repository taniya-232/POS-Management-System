package com.jbs.posbe.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jbs.posbe.entity.AppUser;
import com.jbs.posbe.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final AppUserRepository appUserRepository;

	@Override
	public UserDetails loadUserByUsername(String userEmail) 
			throws UsernameNotFoundException {
		
		AppUser appUser = appUserRepository.findByUserEmailAndActiveTrue(userEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with user email: " + userEmail));
		
		// AppUser implements UserDetails, return the entity instance directly
		return appUser;
	}
	
	
	
	
}