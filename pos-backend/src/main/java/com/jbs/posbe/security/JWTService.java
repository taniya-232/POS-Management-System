package com.jbs.posbe.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jbs.posbe.entity.AppUser;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

@Service
public class JWTService {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expiration}")
	private long jwtExpiration;
	
	// Generate Secret Key
	private SecretKey generateSecretKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	// Generate JWT Token
	public String generateToken(AppUser user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", user.getUserName());
		claims.put("email", user.getUserEmail());
		claims.put("role", user.getUserRole().name());
		return buildToken(claims, user.getUserEmail());
	}
	
	// Build JWT Token
	private String buildToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new java.util.Date(System.currentTimeMillis()))
				.setExpiration(new java.util.Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(generateSecretKey())
				.compact();
	}
	
	// Extract User Email from JWT Token
	public String extractUserEmail(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	// Extract User Role from JWT Token
	public String extractUserRole(String token) {
		return extractAllClaims(token).get("role", String.class);
	}
	
	// Extract User Name from JWT Token
	public String extractUserName(String token) {
		return extractAllClaims(token).get("username", String.class);
	}
	
	// Extract Expiration Date from JWT Token
	public java.util.Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}
	
	// Extract All Claims from JWT Token
	private  Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(generateSecretKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	// Check if JWT Token is Expired
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	// Validate JWT Token
	public boolean validateToken(String token, String userEmail) {
		final String extractedEmail = extractUserEmail(token);
		return (userEmail.equals(extractedEmail) && !isTokenExpired(token));
	}

}
