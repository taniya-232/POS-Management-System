package com.jbs.posbe.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorPatchDto {

    private String vname;
    
    private List<Long> companyIds;

    private Boolean active;
}