package com.jbs.posbe.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.jbs.posbe.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	
	private final JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String senderEmail;

	@Override
	public void sendEmail(String toEmail, String subject, String body) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(senderEmail);
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}

}
