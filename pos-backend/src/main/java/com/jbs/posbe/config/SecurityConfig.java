package com.jbs.posbe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jbs.posbe.security.CustomUserDetailsService;
import com.jbs.posbe.security.JWTAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JWTAuthenticationFilter jwtAuthenticationFilter;
	
	private final CustomUserDetailsService customUserDetailsService;
	
	// Password Encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Authentication Provider
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	// Authentication Manager
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// =====================================================
	// SECURITY FILTER CHAIN
	// =====================================================
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http

			// ENABLE CORS
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))

			// DISABLE CSRF
			.csrf(csrf -> csrf.disable())
			
			// Stateless Session
			.sessionManagement(session -> 
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			
			// URL Authorization
			.authorizeHttpRequests(auth -> auth
					
					// Public End Points
					.requestMatchers(
							"/api/auth/**",
							"/swagger-ui/**",
							"/swagger-ui.html",
							"/v3/api-docs/**"
					).permitAll()
					
					// Everything else required authentication
					.anyRequest()
					.authenticated()	
			)
			
			// authentication Provider
			.authenticationProvider(authenticationProvider())
			
			// JWT Filter
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// =====================================================
	// CORS CONFIGURATION
	// =====================================================

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();

		// FRONTEND URL

		configuration.setAllowedOrigins(List.of("http://localhost:5173"));

		// ALLOWED METHODS

		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

		// ALLOWED HEADERS

		configuration.setAllowedHeaders(List.of("*"));

		// ALLOW CREDENTIALS

		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

}
