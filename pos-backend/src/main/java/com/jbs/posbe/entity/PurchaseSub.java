package com.jbs.posbe.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchase_sub")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseSub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ps_id")
    private Long psId;

    /*
     * Many PurchaseSub -> One PurchaseMaster
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_id", nullable = false)
    @JsonIgnore
    private PurchaseMaster purchaseMaster;

    /*
     * Many PurchaseSub -> One Product
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Double mrp;

    private Integer totalQty;

    private Double grossAmount;

    private Double discountPer;

    private Double discountAmount;

    private Double taxableAmount;

    private Double gstAmount;

    private Double payableAmount;

    private Integer soldQty = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}