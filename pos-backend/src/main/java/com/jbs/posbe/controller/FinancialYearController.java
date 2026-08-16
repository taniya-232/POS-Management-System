package com.jbs.posbe.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.jbs.posbe.dto.ManagedApiResponse;
import com.jbs.posbe.dto.request.FinancialYearPatchDto;
import com.jbs.posbe.dto.request.FinancialYearRequestDto;

import com.jbs.posbe.entity.FinancialYear;
import com.jbs.posbe.service.FinancialYearService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/financialyears")
@RequiredArgsConstructor
public class FinancialYearController {

    private final FinancialYearService
            financialYearService;

    // ------------------------------------------------------
    @Operation(
            tags = "Financial Years",
            summary = "Create a new financial year",
            description =
                    "Creates a new financial year.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description =
                            "Financial Year created successfully"),
            @ApiResponse(
                    responseCode = "500",
                    description =
                            "Error creating financial year")
    })
    @PostMapping
    public ResponseEntity<
            ManagedApiResponse<FinancialYear>>
    saveFinancialYear(
            @Valid
            @RequestBody
            FinancialYearRequestDto dto) {

        FinancialYear financialYear =
                new FinancialYear();

        financialYear.setStartingdt(
                dto.getStartingdt());

        financialYear.setEndingdt(
                dto.getEndingdt());

        financialYear.setFinancialcode(
                dto.getFinancialcode());

        if (dto.getActive() != null) {
            financialYear.setActive(
                    dto.getActive());
        }

        FinancialYear saved =
                financialYearService
                        .saveFinancialYear(
                                financialYear);

        ManagedApiResponse<FinancialYear>
                response =
                new ManagedApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Financial Year created successfully",
                        saved);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ------------------------------------------------------
    @Operation(
            tags = "Financial Years",
            summary = "List financial years",
            description =
                    "Retrieves all financial years.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description =
                            "Financial Years retrieved successfully"),
            @ApiResponse(
                    responseCode = "500",
                    description =
                            "Error retrieving financial years")
    })
    @GetMapping("/page")
    public ResponseEntity<
            ManagedApiResponse<Page<FinancialYear>>>
    getFinancialYears(
            @RequestParam(
                    defaultValue = "0")
            int page,

            @RequestParam(
                    defaultValue = "10")
            int size) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size);

        Page<FinancialYear> fyPage =
                financialYearService
                        .getFinancialYears(
                                pageable);

        ManagedApiResponse<
                Page<FinancialYear>> response =
                new ManagedApiResponse<>(
                        HttpStatus.OK.value(),
                        "Financial Years retrieved successfully",
                        fyPage);

        return ResponseEntity
                .ok(response);
    }

    // ------------------------------------------------------
    @Operation(
            tags = "Financial Years",
            summary =
                    "Get a financial year",
            description =
                    "Fetches a financial year by ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description =
                            "Financial Year retrieved successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description =
                            "Financial Year not found")
    })
    @GetMapping("/{fyId}")
    public ResponseEntity<
            ManagedApiResponse<FinancialYear>>
    getFinancialYearById(
            @PathVariable Long fyId) {

        FinancialYear fy =
                financialYearService
                        .getFinancialYearById(
                                fyId);

        ManagedApiResponse<
                FinancialYear> response =
                new ManagedApiResponse<>(
                        HttpStatus.OK.value(),
                        "Financial Year retrieved successfully",
                        fy);

        return ResponseEntity
                .ok(response);
    }

    // ------------------------------------------------------
    @Operation(
            tags = "Financial Years",
            summary =
                    "Update financial year",
            description =
                    "Updates financial year details.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description =
                            "Financial Year updated successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description =
                            "Financial Year not found"),
            @ApiResponse(
                    responseCode = "400",
                    description =
                            "Invalid request body")
    })
    @PatchMapping("/{fyId}")
    public ResponseEntity<
            ManagedApiResponse<FinancialYear>>
    updateFinancialYear(
            @PathVariable Long fyId,

            @Valid
            @RequestBody
            FinancialYearPatchDto dto) {

        FinancialYear updated =
                financialYearService
                        .updateFinancialYear(
                                fyId,
                                dto);

        ManagedApiResponse<
                FinancialYear> response =
                new ManagedApiResponse<>(
                        HttpStatus.OK.value(),
                        "Financial Year updated successfully",
                        updated);

        return ResponseEntity
                .ok(response);
    }

    // ------------------------------------------------------
    @Operation(
            tags = "Financial Years",
            summary =
                    "Delete financial year",
            description =
                    "Deletes a financial year.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description =
                            "Financial Year deleted successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description =
                            "Financial Year not found"),
            @ApiResponse(
                    responseCode = "500",
                    description =
                            "Error deleting financial year")
    })
    @DeleteMapping("/{fyId}")
    public ResponseEntity<
            ManagedApiResponse<Void>>
    deleteFinancialYear(
            @PathVariable Long fyId) {

        financialYearService
                .deleteFinancialYear(
                        fyId);

        ManagedApiResponse<Void>
                response =
                new ManagedApiResponse<>(
                        HttpStatus.NO_CONTENT.value(),
                        "Financial Year deleted successfully",
                        null);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(response);
    }
}