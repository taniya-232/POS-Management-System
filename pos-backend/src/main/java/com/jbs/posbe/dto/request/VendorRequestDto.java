package com.jbs.posbe.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorRequestDto {

    @NotBlank(message = "Vendor name cannot be blank")
    private String vname;
    
    @NotNull
    private List<Long> companyIds;

    private Boolean active = true;
}