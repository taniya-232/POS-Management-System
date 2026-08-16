package com.jbs.posbe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jbs.posbe.entity.AppUser;
import com.jbs.posbe.enums.UserRole;
import com.jbs.posbe.repository.AppUserRepository;

@SpringBootApplication
public class PosbeApplication implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(PosbeApplication.class);
	
	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;
	
	public PosbeApplication(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
		this.appUserRepository = appUserRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(PosbeApplication.class, args);
		logger.info("PosbeApplication started successfully...");
	}
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("Message from CommandLineRunner...");
		
		// Solving Bootstrap Problem in Authentication System
		// Bootstrap Admin
		if (!appUserRepository.existsByUserEmail("jayanta.b.sen@yopmail.com")) {
			logger.info("Bootstrap Admin not found, creating one...");
			
			AppUser bootstrapAdmin = new AppUser();
			
			bootstrapAdmin.setUserName("Jayanta B Sen");
			bootstrapAdmin.setUserEmail("jayanta.b.sen@yopmail.com");
			bootstrapAdmin.setUserPassword(passwordEncoder.encode("Jayanta@1234"));
			bootstrapAdmin.setUserRole(UserRole.ROLE_ADMIN);
			bootstrapAdmin.setActive(true);
			appUserRepository.save(bootstrapAdmin);
			
			logger.info("Bootstrap Admin created successfully...");
		} else {
			logger.info("Bootstrap Admin already exists...");
		}
	}

}
