package com.jbs.posbe.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorResponseDto {

    private Long vendorId;

    private String vname;

    private List<Long> companyIds;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}