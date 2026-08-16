package com.jbs.posbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jbs.posbe.entity.PurchaseSub;

@Repository
public interface PurchaseSubRepository
        extends JpaRepository<PurchaseSub, Long> {

    /*
     * Get all line items
     * for a purchase invoice
     */
    List<PurchaseSub> findByPurchaseMasterPmId(
            Long pmId);

    /*
     * Product purchase history
     */
    List<PurchaseSub> findByProductProductId(
            Long productId);
}