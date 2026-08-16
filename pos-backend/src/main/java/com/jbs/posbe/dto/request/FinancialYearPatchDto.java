package com.jbs.posbe.dto.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinancialYearPatchDto {

    private LocalDate startingdt;

    private LocalDate endingdt;

    private String financialcode;

    private Boolean active;
}