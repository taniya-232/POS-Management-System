package com.jbs.posbe.dto.request;

import jakarta.validation.constraints.Size;

public class CompanyPatchDto {

	// NO @NotBlank because all fields are optional for PATCH
	@Size(max = 100, message = "Company name cannot exceed 100 characters")
	private String cname;

	// NO @NotBlank because all fields are optional for PATCH
	@Size(max = 10, message = "Company abbreviation cannot exceed 10 characters")
	private String cabbr;

	private Boolean active;

	// ==========================================
	// GETTERS & SETTERS
	// ==========================================

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCabbr() {
		return cabbr;
	}

	public void setCabbr(String cabbr) {
		this.cabbr = cabbr;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
