package com.jbs.posbe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jbs.posbe.dto.request.PurchaseMasterRequestDto;
import com.jbs.posbe.dto.response.PurchaseMasterResponseDto;

public interface PurchaseService {

    /*
     * Create Purchase
     *
     * Responsibilities:
     * - Validate Vendor
     * - Validate Company
     * - Validate Vendor ↔ Company association
     * - Validate Products
     * - Save PurchaseMaster
     * - Save PurchaseSub
     * - Increase Product Stock
     */
    PurchaseMasterResponseDto savePurchase(
            PurchaseMasterRequestDto dto);

    /*
     * Get Purchase Details
     */
    PurchaseMasterResponseDto getPurchaseById(
            Long pmId);

    /*
     * Purchase List
     */
    Page<PurchaseMasterResponseDto> getPurchases(
            Pageable pageable);

    /*
     * Vendor Wise Purchase List
     */
    Page<PurchaseMasterResponseDto> getPurchasesByVendor(
            Long vendorId,
            Pageable pageable);

    /*
     * Company Wise Purchase List
     */
    Page<PurchaseMasterResponseDto> getPurchasesByCompany(
            Long companyId,
            Pageable pageable);

    /*
     * Soft Delete Purchase
     *
     * Responsibilities:
     * - Reverse Stock
     * - Soft Delete PurchaseMaster
     */
    void deletePurchase(
            Long pmId);
}