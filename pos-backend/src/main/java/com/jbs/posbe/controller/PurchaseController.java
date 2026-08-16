package com.jbs.posbe.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import com.jbs.posbe.dto.ManagedApiResponse;
import com.jbs.posbe.dto.request.PurchaseMasterRequestDto;
import com.jbs.posbe.dto.response.PurchaseMasterResponseDto;
import com.jbs.posbe.service.PurchaseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    // ---------------------------------------------------------------------
    @Operation(
            tags = "Purchases",
            summary = "Create a new purchase",
            description = "Creates a new purchase invoice."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Purchase created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid purchase request"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vendor, Company or Product not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error creating purchase"
            )
    })
    @PostMapping
    public ResponseEntity<
            ManagedApiResponse<PurchaseMasterResponseDto>>
    savePurchase(
            @Valid
            @RequestBody
            PurchaseMasterRequestDto dto) {

        PurchaseMasterResponseDto purchase =
                purchaseService.savePurchase(dto);

        ManagedApiResponse<
                PurchaseMasterResponseDto> response =
                new ManagedApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Purchase created successfully",
                        purchase
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ---------------------------------------------------------------------
    @Operation(
            tags = "Purchases",
            summary = "List purchases",
            description = "Retrieves all purchases."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Purchases retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error retrieving purchases"
            )
    })
    @GetMapping("/page")
    public ResponseEntity<
            ManagedApiResponse<
                    Page<PurchaseMasterResponseDto>>>
    getPurchases(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<PurchaseMasterResponseDto>
                purchasePage =
                purchaseService.getPurchases(
                        pageable);

        ManagedApiResponse<
                Page<PurchaseMasterResponseDto>>
                response =
                new ManagedApiResponse<>(
                        HttpStatus.OK.value(),
                        "Purchases retrieved successfully",
                        purchasePage
                );

        return ResponseEntity.ok(response);
    }

    // ---------------------------------------------------------------------
    @Operation(
            tags = "Purchases",
            summary = "Get purchase details",
            description = "Fetches a purchase by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Purchase retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Purchase not found"
            )
    })
    @GetMapping("/{pmId}")
    public ResponseEntity<
            ManagedApiResponse<PurchaseMasterResponseDto>>
    getPurchaseById(
            @PathVariable Long pmId) {

        PurchaseMasterResponseDto purchase =
                purchaseService.getPurchaseById(
                        pmId);

        ManagedApiResponse<
                PurchaseMasterResponseDto> response =
                new ManagedApiResponse<>(
                        HttpStatus.OK.value(),
                        "Purchase retrieved successfully",
                        purchase
                );

        return ResponseEntity.ok(response);
    }

    // ---------------------------------------------------------------------
    @Operation(
            tags = "Purchases",
            summary = "List purchases by vendor",
            description = "Retrieves all purchases for a specific vendor."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Vendor purchases retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vendor not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error retrieving purchases"
            )
    })
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<
            ManagedApiResponse<
                    Page<PurchaseMasterResponseDto>>>
    getPurchasesByVendor(
            @PathVariable Long vendorId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<PurchaseMasterResponseDto>
                purchasePage =
                purchaseService
                        .getPurchasesByVendor(
                                vendorId,
                                pageable);

        ManagedApiResponse<
                Page<PurchaseMasterResponseDto>>
                response =
                new ManagedApiResponse<>(
                        HttpStatus.OK.value(),
                        "Vendor purchases retrieved successfully",
                        purchasePage
                );

        return ResponseEntity.ok(response);
    }

    // ---------------------------------------------------------------------
    @Operation(
            tags = "Purchases",
            summary = "List purchases by company",
            description = "Retrieves all purchases for a specific company."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Company purchases retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Company not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error retrieving purchases"
            )
    })
    @GetMapping("/company/{companyId}")
    public ResponseEntity<
            ManagedApiResponse<
                    Page<PurchaseMasterResponseDto>>>
    getPurchasesByCompany(
            @PathVariable Long companyId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<PurchaseMasterResponseDto>
                purchasePage =
                purchaseService
                        .getPurchasesByCompany(
                                companyId,
                                pageable);

        ManagedApiResponse<
                Page<PurchaseMasterResponseDto>>
                response =
                new ManagedApiResponse<>(
                        HttpStatus.OK.value(),
                        "Company purchases retrieved successfully",
                        purchasePage
                );

        return ResponseEntity.ok(response);
    }

    // ---------------------------------------------------------------------
    @Operation(
            tags = "Purchases",
            summary = "Delete a purchase by ID",
            description = "Deletes a purchase and reverses stock."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Purchase deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Purchase not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Stock reversal validation failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error deleting purchase"
            )
    })
    @DeleteMapping("/{pmId}")
    public ResponseEntity<
            ManagedApiResponse<Void>>
    deletePurchase(
            @PathVariable Long pmId) {

        purchaseService.deletePurchase(pmId);

        ManagedApiResponse<Void>
                response =
                new ManagedApiResponse<>(
                        HttpStatus.NO_CONTENT.value(),
                        "Purchase deleted successfully",
                        null
                );

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(response);
    }
}