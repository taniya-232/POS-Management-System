package com.jbs.posbe.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseMasterRequestDto {

    @NotNull(message = "Vendor Id is required")
    private Long vendorId;

    @NotNull(message = "Company Id is required")
    private Long companyId;

    @NotBlank(message = "Invoice number is required")
    private String invoiceNo;

    @NotNull(message = "Invoice date is required")
    private LocalDate invoiceDate;

    @NotNull(message = "Net item count is required")
    private Integer netItemCount;

    @NotNull(message = "Net gross amount is required")
    private Double netGross;

    private Double netDiscount = 0.0;

    @NotNull(message = "Taxable amount is required")
    private Double taxableAmount;

    @NotNull(message = "Net GST amount is required")
    private Double netGstAmount;

    @NotNull(message = "Net payable amount is required")
    private Double netPayableAmount;

    @NotNull(message = "Final amount is required")
    private Double finalAmount;

    @NotEmpty(message = "Purchase items cannot be empty")
    @Valid
    private List<PurchaseSubRequestDto> items;
}