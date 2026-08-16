package com.jbs.posbe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {

	@NotNull(message = "Company ID is required")
	private Long companyId;

	@NotNull(message = "Unit ID is required")
	private Long unitId;

	@NotBlank(message = "Product name is required")
	private String pname;

	private String hsn;

	@NotNull(message = "GST is required")
	private Float gst;

	@NotNull(message = "Unit price is required")
	private Float unitPrice;

	@NotNull(message = "Stock is required")
	private Integer stock;

	private Boolean active = true;
}
