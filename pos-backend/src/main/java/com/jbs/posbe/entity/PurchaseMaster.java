package com.jbs.posbe.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchase_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE purchase_master SET deleted = true WHERE pm_id = ?")
@SQLRestriction("deleted = false")
public class PurchaseMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pm_id")
    private Long pmId;

    /*
     * Many PurchaseMaster -> One Vendor
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    /*
     * Many PurchaseMaster -> One Company
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String invoiceNo;

    @Column(nullable = false)
    private LocalDate invoiceDate;

    private Integer netItemCount;

    private Double netGross;

    private Double netDiscount;

    private Double taxableAmount;

    private Double netGstAmount;

    private Double netPayableAmount;

    private Double finalAmount;

    @Column(nullable = false,
            columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

    @Column(nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /*
     * One PurchaseMaster -> Many PurchaseSub
     */
    @OneToMany(
            mappedBy = "purchaseMaster",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<PurchaseSub> purchaseItems;
}