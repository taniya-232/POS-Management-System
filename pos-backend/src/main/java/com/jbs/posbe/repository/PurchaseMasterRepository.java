package com.jbs.posbe.repository;

//import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import com.jbs.posbe.entity.PurchaseMaster;

@Repository
public interface PurchaseMasterRepository
        extends JpaRepository<PurchaseMaster, Long> {

    /*
     * Check duplicate invoice number
     */
    boolean existsByInvoiceNo(String invoiceNo);

    /*
     * Fetch Purchase with PurchaseSub
     */
    @EntityGraph(attributePaths = {
            "purchaseItems",
            "purchaseItems.product",
            "vendor",
            "company"
    })
    Optional<PurchaseMaster> findWithDetailsByPmId(
            Long pmId);

    /*
     * Vendor wise purchase list
     */
    Page<PurchaseMaster> findByVendorVendorId(
            Long vendorId,
            Pageable pageable);

    /*
     * Company wise purchase list
     */
    Page<PurchaseMaster> findByCompanyCompanyId(
            Long companyId,
            Pageable pageable);

    /*
     * Invoice search
     */
    Optional<PurchaseMaster> findByInvoiceNo(
            String invoiceNo);
    
    // For later use if required
    
    // Purchase Register Reports
//    Page<PurchaseMaster> findByInvoiceDateBetween(
//            LocalDate start,
//            LocalDate end,
//            Pageable pageable);
    
    // Vendor Ledger Reports
//    Page<PurchaseMaster> findByVendorVendorIdAndInvoiceDateBetween(
//            Long vendorId,
//            LocalDate from,
//            LocalDate to,
//            Pageable pageable);
}