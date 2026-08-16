package com.jbs.posbe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jbs.posbe.dto.ManagedApiResponse;
import com.jbs.posbe.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailTestController {
	
	private final EmailService emailService;
	
	@GetMapping("/test")
	public ResponseEntity<ManagedApiResponse<Void>> sendTestEmail(@RequestParam String toEmail) {
	
		String subject = "Test Email from POSBE";
		String body = "This is a test email sent from the POSBE application.";
		
		emailService.sendEmail(toEmail, subject, body);
		
		ManagedApiResponse<Void> response = new ManagedApiResponse<>(
				200,
				"Test email sent successfully",
				null
		);
		
		return ResponseEntity.ok(response);
	}

}
