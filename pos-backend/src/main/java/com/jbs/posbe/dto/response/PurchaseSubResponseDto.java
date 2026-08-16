package com.jbs.posbe.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseSubResponseDto {

    private Long psId;

    private Long productId;

    private String productName;

    private Double mrp;

    private Integer totalQty;

    private Double grossAmount;

    private Double discountPer;

    private Double discountAmount;

    private Double taxableAmount;

    private Double gstAmount;

    private Double payableAmount;

    private Integer soldQty;
}