package com.jbs.posbe.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jbs.posbe.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE app_users SET deleted = true WHERE user_id = ?")
@SQLRestriction("deleted = false")
@Builder
public class AppUser implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	
	@Column(nullable = false)
	private String userName;
	
	@Column(nullable = false, unique = true)
	private String userEmail;
	
	/*
	 *  BCrypt Encrypted Password:
	 */
	@Column(nullable = false)
	private String userPassword;
	
	/*
	 * ROLE_ADMIN
	 * ROLE_USER
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole userRole;
	
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean deleted;
	
	@Column(nullable = false)
	private boolean active=true;
	
	@CreationTimestamp
    @Column(updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	public String getUserName() {
		return this.userName;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.userRole == null) {
			return Collections.emptyList();
		}
		return Collections.singletonList(new SimpleGrantedAuthority(this.userRole.name()));
	}

	@Override
	public String getPassword() {
		return this.userPassword;
	}

	@Override
	public String getUsername() {
		// use email as the principal
		return this.userEmail;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.active && !this.deleted;
	}
	
	/*
	 * One User -> Many OTPs
	 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<UserOtp> userOTPs;
	
}