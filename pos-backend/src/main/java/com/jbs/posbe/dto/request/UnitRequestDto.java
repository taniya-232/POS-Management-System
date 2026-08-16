package com.jbs.posbe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitRequestDto {

    @NotBlank
    private String uname;

    @NotBlank
    private String uabbr;

    private Boolean active = true;
}