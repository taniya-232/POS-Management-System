package com.jbs.posbe.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "units")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE units SET deleted = true WHERE unit_id = ?")
@SQLRestriction("deleted = false")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long unitId;

    @Column(nullable = false, unique = true)
    private String uname;

    @Column(nullable = false, unique = true)
    private String uabbr;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /*
     * One Unit -> Many Products
     */
    @OneToMany(
            mappedBy = "unit",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Product> products;
}