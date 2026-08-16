package com.jbs.posbe.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinancialYearRequestDto {

    @NotNull(message = "Starting date cannot be null")
    private LocalDate startingdt;

    @NotNull(message = "Ending date cannot be null")
    private LocalDate endingdt;

    @NotBlank(message = "Financial code cannot be blank")
    private String financialcode;

    private Boolean active = true;
}