package com.jbs.posbe.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseMasterResponseDto {

    private Long pmId;

    private Long vendorId;

    private String vendorName;

    private Long companyId;

    private String companyName;

    private String invoiceNo;

    private LocalDate invoiceDate;

    private Integer netItemCount;

    private Double netGross;

    private Double netDiscount;

    private Double taxableAmount;

    private Double netGstAmount;

    private Double netPayableAmount;

    private Double finalAmount;

    private Boolean active;

    private List<PurchaseSubResponseDto> items;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}