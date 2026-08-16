package com.jbs.posbe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CompanyRequestDto {
	
	@NotBlank(message = "Company name is required")
    @Size(max = 100,
          message = "Company name cannot exceed 100 characters")
    private String cname;

    @NotBlank(message = "Company abbreviation is required")
    @Size(max = 10,
          message = "Company abbreviation cannot exceed 10 characters")
    private String cabbr;

    private Boolean active = true;

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
