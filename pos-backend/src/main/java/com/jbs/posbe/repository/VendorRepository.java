package com.jbs.posbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jbs.posbe.entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

}