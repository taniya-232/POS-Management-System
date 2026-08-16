package com.jbs.posbe.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPatchDto {

    private Long companyId;
    
    private Long unitId;

    private String pname;

    private String hsn;

    private Float gst;

    private Float unitPrice;

    private Integer stock;

    private Boolean active;
}