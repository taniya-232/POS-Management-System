package com.jbs.posbe.entity;

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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE companies SET deleted = true WHERE company_id = ?")
@SQLRestriction("deleted = false")
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Long companyId;
	
	@Column(nullable = false)
	private String cname;
	
	private String cabbr;
	
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean deleted;
	
	@Column(nullable = false)
	private boolean active=true;
	
	@CreationTimestamp
    @Column(updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Product> products;
    
    @ManyToMany(
            mappedBy = "companies",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Vendor> vendors;

}
