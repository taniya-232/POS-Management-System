package com.jbs.posbe.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jbs.posbe.service.AppUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.jbs.posbe.dto.request.AppUserPatchDto;
import com.jbs.posbe.dto.request.AppUserRequestDto;
import com.jbs.posbe.dto.response.AppUserResponseDto;
import com.jbs.posbe.dto.ManagedApiResponse;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {
	
	private final AppUserService appUserService;
	
	// ---------------------------------------------------------------------
	@Operation(
			tags = "Users", summary = "Create a new user", description = "Creates a new application user.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "User created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request data"),
			@ApiResponse(responseCode = "500", description = "Error creating user") })
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ManagedApiResponse<AppUserResponseDto>> saveUser(
			@Valid @RequestBody AppUserRequestDto dto) {
		
		AppUserResponseDto savedUser = appUserService.saveUser(dto);
		
		ManagedApiResponse<AppUserResponseDto> response = new ManagedApiResponse<>(
						HttpStatus.CREATED.value(),
						"User created successfully",
						savedUser
				);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	// ---------------------------------------------------------------------
	@Operation(
			tags = "Users", summary = "List users", description = "Retrieve all users.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "User created successfully") })
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/page")
	public ResponseEntity<ManagedApiResponse<Page<AppUserResponseDto>>> getAllUsers(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		
		var pageable = PageRequest.of(page, size);
		
		Page<AppUserResponseDto> usersPage = appUserService.getAllUsers(pageable);
		
		ManagedApiResponse<Page<AppUserResponseDto>> response = new ManagedApiResponse<>(
				HttpStatus.OK.value(),
				"Users retrieved successfully",
				usersPage
		);
		
		return ResponseEntity.ok(response);
	}
	
	// ---------------------------------------------------------------------
	@Operation(
			tags = "Users", summary = "Get users by ID", description = "Fetch user by ID.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "User created successfully"),
			@ApiResponse(responseCode = "404", description = "User not found") })
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{userId}")
	public ResponseEntity<ManagedApiResponse<AppUserResponseDto>> getUserById(@PathVariable Long userId) {
		
		AppUserResponseDto user = appUserService.getUserById(userId);
		
		ManagedApiResponse<AppUserResponseDto> response = new ManagedApiResponse<>(
				HttpStatus.OK.value(),
				"User retrieved successfully",
				user
		);
		
		return ResponseEntity.ok(response);
	}
	
	// ---------------------------------------------------------------------
	@Operation(
			tags = "Users", summary = "Update user", description = "Update user details (partial/total).")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "User updated successfully") })
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{userId}")
	public ResponseEntity<ManagedApiResponse<AppUserResponseDto>> updateUser(
			@PathVariable Long userId,
			@Valid @RequestBody AppUserPatchDto dto) {
		
		AppUserResponseDto updatedUser = appUserService.updateUser(userId, dto);
		
		ManagedApiResponse<AppUserResponseDto> response = new ManagedApiResponse<>(
				HttpStatus.OK.value(),
				"User updated successfully",
				updatedUser
		);
		
		return ResponseEntity.ok(response);
	}
	
	// ---------------------------------------------------------------------
	@Operation(
			tags = "Users", summary = "Delete user", description = "Delete user by ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "User deleted successfully"),
			@ApiResponse(responseCode = "404", description = "User not found")
	})
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ManagedApiResponse<Void>> deleteUser(@PathVariable Long userId) {
		
		appUserService.deleteUser(userId);
		
		ManagedApiResponse<Void> response = new ManagedApiResponse<>(
				HttpStatus.NO_CONTENT.value(),
				"User deleted successfully",
				null
		);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	}
}
