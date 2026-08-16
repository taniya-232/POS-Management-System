package com.jbs.posbe.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseSubRequestDto {

    @NotNull(message = "Product Id is required")
    private Long productId;

    @NotNull(message = "MRP is required")
    @Min(value = 0, message = "MRP cannot be negative")
    private Double mrp;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity should be at least 1")
    private Integer totalQty;

    @NotNull(message = "Gross amount is required")
    @Min(value = 0, message = "Gross amount cannot be negative")
    private Double grossAmount;

    private Double discountPer = 0.0;

    private Double discountAmount = 0.0;

    @NotNull(message = "Taxable amount is required")
    private Double taxableAmount;

    @NotNull(message = "GST amount is required")
    private Double gstAmount;

    @NotNull(message = "Payable amount is required")
    private Double payableAmount;
}