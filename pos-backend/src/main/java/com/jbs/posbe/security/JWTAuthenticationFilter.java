package com.jbs.posbe.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final JWTService jwtService;
	private final CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
			throws ServletException, IOException {
		
		// Read Authorization header and validate JWT
		final String authHeader = request.getHeader("Authorization");
		
		// No JWT token found in header, proceed with next filter
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final String jwtToken = authHeader.substring(7); // Remove "Bearer " prefix
		
		// Extract email
		final String userEmail = jwtService.extractUserEmail(jwtToken);
		
		// Authenticate user if email is valid and not already authenticated
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
			
			// Validate JWT token
			if (jwtService.validateToken(jwtToken, userEmail)) {
				UsernamePasswordAuthenticationToken authToken =	new UsernamePasswordAuthenticationToken(userDetails, 
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		// Continue Request
		filterChain.doFilter(request, response);
	}

}
