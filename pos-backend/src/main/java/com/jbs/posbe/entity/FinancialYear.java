package com.jbs.posbe.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "financial_years")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE financial_years SET deleted = true WHERE fy_id = ?")
@SQLRestriction("deleted = false")
public class FinancialYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fy_id")
    private Long fyId;

    @Column(nullable = false)
    private LocalDate startingdt;

    @Column(nullable = false)
    private LocalDate endingdt;

    @Column(nullable = false, unique = true)
    private String financialcode;
    
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean deleted;

    @Column(nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}